package back.vybz.live_service.live.dto.request;

import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class RequestAddLiveDto {

    private String title;
    private String buskerUuid;
    private String streamKey;

    @Builder
    public RequestAddLiveDto(String title,
                             String buskerUuid,
                             String streamKey) {
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.streamKey = streamKey;
    }

    public static RequestAddLiveDto from(RequestAddLiveVo requestAddLiveVo, String buskerUuid, String streamKey) {
        return RequestAddLiveDto.builder()
                .title(requestAddLiveVo.getTitle())
                .buskerUuid(buskerUuid)
                .streamKey(streamKey)
                .build();
    }

    public LiveStream toEntity(){
        return LiveStream.builder()
                .buskerUuid(buskerUuid)
                .streamKey(streamKey)
                .title(title)
                .liveStreamStatus(LiveStreamStatus.ON_AIR)
                .likeCount(0)
                .viewerCount(0)
                .startTime(Instant.now())
                .build();
    }

    }

