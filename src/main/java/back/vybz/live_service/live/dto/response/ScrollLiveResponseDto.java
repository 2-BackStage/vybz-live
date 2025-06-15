package back.vybz.live_service.live.dto.response;

import back.vybz.live_service.common.util.CursorPage;
import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.vo.response.ScrollLiveResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScrollLiveResponseDto {

    private List<ScrollLiveResponseVo> content;
    private boolean hasNext;
    private String nextCursor;

    @Builder
    public ScrollLiveResponseDto(List<ScrollLiveResponseVo> content,
                                 boolean hasNext,
                                 String nextCursor) {
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

    public static ScrollLiveResponseDto from(CursorPage<LiveStream> cursorPage) {
        return ScrollLiveResponseDto.builder()
                .content(ScrollLiveResponseVo.listFrom(cursorPage.getContent()))
                .hasNext(cursorPage.getHasNext())
                .nextCursor(cursorPage.getNextCursor())
                .build();
    }


}
