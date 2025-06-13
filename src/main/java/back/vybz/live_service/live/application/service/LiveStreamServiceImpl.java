package back.vybz.live_service.live.application.service;

import back.vybz.live_service.common.exception.BaseException;
import back.vybz.live_service.common.exception.BaseResponseStatus;
import back.vybz.live_service.common.util.LiveRedisService;
import back.vybz.live_service.common.util.StreamKeyGenerator;
import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService{

    private final LiveStreamRepository liveStreamRepository;
    private final LiveRedisService liveRedisService;


    @Override
    @Transactional
    public ResponseAddLiveDto createLiveStream(RequestAddLiveDto requestAddLiveDto, String buskerUuid) {
        String streamKey = StreamKeyGenerator.generate();


        RequestAddLiveDto newDto = RequestAddLiveDto.builder()
                .title(requestAddLiveDto.getTitle())
                .buskerUuid(buskerUuid)
                .streamKey(streamKey)
                .build();

        LiveStream liveStream = newDto.toEntity();
        LiveStream saved = liveStreamRepository.save(liveStream);

        liveRedisService.saveLiveStreamToRedis(saved);

        String hlsUrl = "http://localhost:8090/hls/" + streamKey + ".m3u8";

        return ResponseAddLiveDto.builder()
                .id(saved.getId())
                .streamKey(saved.getStreamKey())
                .hlsUrl(hlsUrl)
                .liveStreamStatus(saved.getLiveStreamStatus())
                .build();
    }
    @Override
    @Transactional
    public void endLiveStream(String buskerUuid, String streamKey){
        LiveStream liveStream = liveStreamRepository.findByBuskerUuidAndStreamKey(buskerUuid, streamKey)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LIVE_STREAM_NOT_FOUND));

        liveStream.endLiveStream();
        liveStreamRepository.save(liveStream);

        liveRedisService.removeLiveStreamFromRedis(buskerUuid);
    }


}
