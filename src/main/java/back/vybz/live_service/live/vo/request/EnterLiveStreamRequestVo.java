package back.vybz.live_service.live.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterLiveStreamRequestVo {

    private String streamKey;
    private String viewerUuid;

    @Builder
    public EnterLiveStreamRequestVo(String streamKey,
                                    String viewerUuid) {
        this.streamKey = streamKey;
        this.viewerUuid = viewerUuid;
    }
}
