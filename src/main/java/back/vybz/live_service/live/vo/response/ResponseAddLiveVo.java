package back.vybz.live_service.live.vo.response;

import back.vybz.live_service.live.domain.LiveStreamStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseAddLiveVo {
    private String id;
    private String streamKey;
    private String hlsUrl;
    private LiveStreamStatus liveStreamStatus;

    @Builder
    public ResponseAddLiveVo(String id,
                             String streamKey,
                             String hlsUrl,
                             LiveStreamStatus liveStreamStatus) {
        this.id = id;
        this.streamKey = streamKey;
        this.hlsUrl = hlsUrl;
        this.liveStreamStatus = liveStreamStatus;
    }
}
