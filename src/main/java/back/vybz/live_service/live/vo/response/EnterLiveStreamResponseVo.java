package back.vybz.live_service.live.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterLiveStreamResponseVo {

    private String title;
    private String buskerUuid;
    private int viewerCount;
    private boolean isAlreadyWatching;
    private String hlsUrl;

    @Builder
    public EnterLiveStreamResponseVo(String title,
                                      String buskerUuid,
                                      int viewerCount,
                                      boolean isAlreadyWatching,
                                      String hlsUrl) {
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.viewerCount = viewerCount;
        this.isAlreadyWatching = isAlreadyWatching;
        this.hlsUrl = hlsUrl;
    }
}
