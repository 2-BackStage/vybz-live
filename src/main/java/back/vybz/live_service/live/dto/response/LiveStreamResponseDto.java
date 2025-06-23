package back.vybz.live_service.live.dto.response;

import back.vybz.live_service.live.vo.response.LiveStreamResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamResponseDto {

    private String title;
    private String buskerUuid;
    private int likeCount;
    private int viewerCount;
    private boolean isAlreadyWatching;
    private String hlsUrl;
    private Long categoryId;

    @Builder
    public LiveStreamResponseDto(String title,
                                 String buskerUuid,
                                 int likeCount,
                                 int viewerCount,
                                 boolean isAlreadyWatching,
                                 String hlsUrl,
                                 Long categoryId) {
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.likeCount = likeCount;
        this.viewerCount = viewerCount;
        this.isAlreadyWatching = isAlreadyWatching;
        this.hlsUrl = hlsUrl;
        this.categoryId = categoryId;
    }

    public LiveStreamResponseVo toVo() {
        return LiveStreamResponseVo.builder()
                .title(title)
                .buskerUuid(buskerUuid)
                .viewerCount(viewerCount)
                .likeCount(likeCount)
                .hlsUrl(hlsUrl)
                .categoryId(categoryId)
                .build();
    }

}
