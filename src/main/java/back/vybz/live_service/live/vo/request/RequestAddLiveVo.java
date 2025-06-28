package back.vybz.live_service.live.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddLiveVo {

    private String title;
    private Long categoryId;
    private boolean membership;

    @Builder
    public RequestAddLiveVo(String title,
                            Long categoryId,
                            boolean membership) {
        this.title = title;
        this.categoryId = categoryId;
        this.membership = membership;
    }

}
