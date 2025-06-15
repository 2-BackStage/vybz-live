package back.vybz.live_service.live.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrollLiveRequestDto {

    private String lastId;
    private Long categoryId;
    private int size;

    @Builder
    public ScrollLiveRequestDto(String lastId,
                                Long categoryId,
                                int size) {
        this.lastId = lastId;
        this.categoryId = categoryId;
        this.size = size;
    }

}
