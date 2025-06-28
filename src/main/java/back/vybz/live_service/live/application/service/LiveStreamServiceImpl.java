package back.vybz.live_service.live.application.service;

import back.vybz.live_service.common.client.SupportServiceClient;
import back.vybz.live_service.common.exception.BaseException;
import back.vybz.live_service.common.exception.BaseResponseStatus;
import back.vybz.live_service.common.util.CursorPage;
import back.vybz.live_service.common.util.StreamKeyGenerator;
import back.vybz.live_service.common.util.ViewerWebSocketHandler;
import back.vybz.live_service.kafka.event.ViewCountKafkaEvent;
import back.vybz.live_service.kafka.producer.ViewCountKafkaEventProducer;
import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.request.ScrollLiveRequestDto;
import back.vybz.live_service.live.dto.response.LiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.dto.response.ScrollLiveResponseDto;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiveStreamServiceImpl implements LiveStreamService {

    private final LiveStreamRepository liveStreamRepository;
    private final ViewerWebSocketHandler viewerWebSocketHandler;
    private final ViewCountKafkaEventProducer viewCountKafkaEventProducer;
    private final SupportServiceClient supportServiceClient;

    @Transactional
    @Override
    public ResponseAddLiveDto createLiveStream(RequestAddLiveDto requestAddLiveDto, String buskerUuid) {
        String streamKey = StreamKeyGenerator.generate();

        LiveStream liveStream = LiveStream.builder()
                .buskerUuid(buskerUuid)
                .title(requestAddLiveDto.getTitle())
                .streamKey(streamKey)
                .liveStreamStatus(LiveStreamStatus.ON_AIR)
                .categoryId(requestAddLiveDto.getCategoryId())
                .membership(requestAddLiveDto.isMembership())
                .startTime(Instant.now())
                .likeCount(0)
                .viewerCount(0)
                .build();

        LiveStream saved = liveStreamRepository.save(liveStream);

        return ResponseAddLiveDto.builder()
                .id(saved.getId())
                .streamKey(saved.getStreamKey())
                .liveStreamStatus(saved.getLiveStreamStatus())
                .categoryId(saved.getCategoryId())
                .membership(saved.isMembership())
                .build();
    }

    @Override
    @Transactional
    public void endLiveStream(String buskerUuid, String streamKey) {
        LiveStream liveStream = liveStreamRepository.findByBuskerUuidAndStreamKey(buskerUuid, streamKey)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LIVE_STREAM_NOT_FOUND));

        liveStream.endLiveStream();
        liveStreamRepository.save(liveStream);

        viewerWebSocketHandler.notifyStreamEnded(streamKey);
    }

    @Override
    public LiveStreamResponseDto getLiveStream(String streamKey, String viewerUuid) {

        LiveStream liveStream = liveStreamRepository.findByStreamKey(streamKey)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LIVE_STREAM_NOT_FOUND));

        // 구독자 전용 라이브 검증
        if (liveStream.isMembership()) {
            if (!validateMembershipAccess(liveStream.getBuskerUuid(), viewerUuid)) {
                throw new BaseException(BaseResponseStatus.MEMBERSHIP_ONLY_LIVE);
            }
        }

        viewCountKafkaEventProducer.send(
                ViewCountKafkaEvent.builder()
                        .streamKey(streamKey)
                        .viewerUuid(viewerUuid)
                        .build()
        );

        return LiveStreamResponseDto.builder()
                .title(liveStream.getTitle())
                .buskerUuid(liveStream.getBuskerUuid())
                .likeCount(liveStream.getLikeCount())
                .viewerCount(liveStream.getViewerCount() + 1)
                .streamKey(liveStream.getStreamKey())
                .categoryId(liveStream.getCategoryId())
                .membership(liveStream.isMembership())
                .build();
    }

    /**
     * 구독자 전용 라이브 접근 권한 검증
     * Support 서비스에서 구독 정보 조회
     */
    private boolean validateMembershipAccess(String buskerUuid, String viewerUuid) {
        try {
            log.info("구독자 검증 시작: buskerUuid={}, viewerUuid={}", buskerUuid, viewerUuid);
            
            // Support 서비스에서 구독 상태 조회
            boolean isSubscribed = supportServiceClient.checkSubscriptionStatus(buskerUuid, viewerUuid);
            
            log.info("구독자 검증 완료: buskerUuid={}, viewerUuid={}, isSubscribed={}", 
                    buskerUuid, viewerUuid, isSubscribed);
            
            return isSubscribed;
            
        } catch (Exception e) {
            log.error("구독자 검증 중 에러 발생: buskerUuid={}, viewerUuid={}", buskerUuid, viewerUuid, e);
            // 에러 발생 시 보안을 위해 접근 차단
            return false;
        }
    }

    @Override
    public ScrollLiveResponseDto getLiveStreamScrollList(ScrollLiveRequestDto scrollLiveRequestDto) {
        String cursor = scrollLiveRequestDto.getLastId();

        List<LiveStream> liveStreams = liveStreamRepository.findLiveStreamAllWithScroll(
                cursor,
                scrollLiveRequestDto.getSize()
        );

        CursorPage<LiveStream> cursorPage = CursorPage.of(
                liveStreams,
                scrollLiveRequestDto.getSize(),
                LiveStream::getId
        );

        return ScrollLiveResponseDto.from(cursorPage);
    }

    @Override
    public ScrollLiveResponseDto getLiveStreamScrollListByCategory(ScrollLiveRequestDto scrollLiveRequestDto) {
        String cursor = scrollLiveRequestDto.getLastId();
        Long categoryId = scrollLiveRequestDto.getCategoryId();

        if (categoryId == null) {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }

        List<LiveStream> liveStreams = liveStreamRepository.findLiveStreamWithScroll(
                cursor,
                categoryId,
                scrollLiveRequestDto.getSize()
        );

        CursorPage<LiveStream> cursorPage = CursorPage.of(
                liveStreams,
                scrollLiveRequestDto.getSize(),
                LiveStream::getId
        );

        return ScrollLiveResponseDto.from(cursorPage);
    }
}
