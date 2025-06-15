package back.vybz.live_service.live.dto.response;

import back.vybz.live_service.live.vo.response.EnterLiveStreamResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterLiveStreamResponseDto {

    private String title;
    private String buskerUuid;
    private int viewerCount;
    private boolean isAlreadyWatching;
    private String hlsUrl;

    @Builder
    public EnterLiveStreamResponseDto(String title,
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

    public EnterLiveStreamResponseVo toVo() {
        return EnterLiveStreamResponseVo.builder()
                .title(title)
                .buskerUuid(buskerUuid)
                .viewerCount(viewerCount)
                .isAlreadyWatching(isAlreadyWatching)
                .hlsUrl(hlsUrl)
                .build();
    }

}
