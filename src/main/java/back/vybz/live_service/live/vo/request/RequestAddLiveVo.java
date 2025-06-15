package back.vybz.live_service.live.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddLiveVo {

    private String title;
    private Long categoryId;

    @Builder
    public RequestAddLiveVo(String title,
                            Long categoryId) {
        this.title = title;
        this.categoryId = categoryId;
    }

}
