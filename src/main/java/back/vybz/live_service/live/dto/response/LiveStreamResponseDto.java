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
    private String streamKey;
    private Long categoryId;
    private boolean membership;

    @Builder
    public LiveStreamResponseDto(String title,
                                 String buskerUuid,
                                 int likeCount,
                                 int viewerCount,
                                 String streamKey,
                                 Long categoryId,
                                 boolean membership) {
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.likeCount = likeCount;
        this.viewerCount = viewerCount;
        this.streamKey = streamKey;
        this.categoryId = categoryId;
        this.membership = membership;
    }

    public LiveStreamResponseVo toVo() {
        return LiveStreamResponseVo.builder()
                .title(title)
                .buskerUuid(buskerUuid)
                .viewerCount(viewerCount)
                .likeCount(likeCount)
                .streamKey(streamKey)
                .categoryId(categoryId)
                .membership(membership)
                .build();
    }
}
