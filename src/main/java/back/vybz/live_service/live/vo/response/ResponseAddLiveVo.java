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
    private LiveStreamStatus liveStreamStatus;
    private Long categoryId;
    private boolean membership;

    @Builder
    public ResponseAddLiveVo(String id, String streamKey, LiveStreamStatus liveStreamStatus, Long categoryId, boolean membership) {
        this.id = id;
        this.streamKey = streamKey;
        this.liveStreamStatus = liveStreamStatus;
        this.categoryId = categoryId;
        this.membership = membership;
    }
}

