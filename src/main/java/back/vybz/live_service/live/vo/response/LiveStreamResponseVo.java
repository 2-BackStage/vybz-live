package back.vybz.live_service.live.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamResponseVo {

    private String title;
    private String buskerUuid;
    private int likeCount;
    private int viewerCount;
    private String streamKey;
    private Long categoryId;
    private boolean membership;

    @Builder
    public LiveStreamResponseVo(String title,
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
}
