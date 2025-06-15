package back.vybz.live_service.live.application.service;

import back.vybz.live_service.common.exception.BaseException;
import back.vybz.live_service.common.exception.BaseResponseStatus;
import back.vybz.live_service.common.util.LiveRedisService;
import back.vybz.live_service.common.util.StreamKeyGenerator;
import back.vybz.live_service.common.util.ViewerWebSocketHandler;
import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import back.vybz.live_service.live.dto.request.EnterLiveStreamRequestDto;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.response.EnterLiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService {

    private final LiveStreamRepository liveStreamRepository;
    private final LiveRedisService liveRedisService;
    private final ViewerWebSocketHandler viewerWebSocketHandler;
    private final BuskerFeignClient buskerFeignClient;


    @Transactional
    @Override
    public ResponseAddLiveDto createLiveStream(RequestAddLiveDto requestAddLiveDto, String buskerUuid) {
        String streamKey = StreamKeyGenerator.generate();

        Long categoryId = buskerFeignClient.getMainCategoryByBusker(buskerUuid).result().getCategoryId();

        LiveStream liveStream = LiveStream.builder()
                .buskerUuid(buskerUuid)
                .title(requestAddLiveDto.getTitle())
                .streamKey(streamKey)
                .liveStreamStatus(LiveStreamStatus.ON_AIR)
                .categoryId(categoryId)
                .startTime(Instant.now())
                .likeCount(0)
                .viewerCount(0)
                .build();


        LiveStream saved = liveStreamRepository.save(liveStream);
        liveRedisService.saveLiveStreamToRedis(saved);

        String hlsUrl = "http://localhost:8090/hls/" + streamKey + ".m3u8";

        return ResponseAddLiveDto.builder()
                .id(saved.getId())
                .streamKey(saved.getStreamKey())
                .hlsUrl(hlsUrl)
                .liveStreamStatus(saved.getLiveStreamStatus())
                .categoryId(saved.getCategoryId())
                .build();
    }


    @Override
    @Transactional
    public void endLiveStream(String buskerUuid, String streamKey) {
        LiveStream liveStream = liveStreamRepository.findByBuskerUuidAndStreamKey(buskerUuid, streamKey)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LIVE_STREAM_NOT_FOUND));

        liveStream.endLiveStream();
        liveStreamRepository.save(liveStream);

        liveRedisService.removeLiveStreamFromRedis(buskerUuid);
        viewerWebSocketHandler.notifyStreamEnded(streamKey);
    }

    @Override
    @Transactional
    public EnterLiveStreamResponseDto enterLiveStream(EnterLiveStreamRequestDto enterLiveStreamRequestDto, String viewerUuid) {
        String streamKey = enterLiveStreamRequestDto.getStreamKey();

        LiveStream liveStream = liveRedisService.getLiveStreamFromRedis(streamKey)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LIVE_STREAM_NOT_FOUND));

        boolean isAlreadyWatching = liveRedisService.isViewerAlreadyWatching(streamKey, viewerUuid);

        if (!isAlreadyWatching) {
            liveRedisService.enterViewer(streamKey, viewerUuid);
        }

        int viewerCount = liveRedisService.getViewerCount(streamKey);

        String hlsUrl = "http://localhost:8090/hls/" + streamKey + ".m3u8";

        return EnterLiveStreamResponseDto.builder()
                .title(liveStream.getTitle())
                .buskerUuid(liveStream.getBuskerUuid())
                .viewerCount(viewerCount)
                .isAlreadyWatching(isAlreadyWatching)
                .hlsUrl(hlsUrl)
                .build();
    }

    @Override
    @Transactional
    public void existLiveStream(String streamKey, String viewerUuid){
        liveRedisService.exitViewer(streamKey, viewerUuid);
    }
}



