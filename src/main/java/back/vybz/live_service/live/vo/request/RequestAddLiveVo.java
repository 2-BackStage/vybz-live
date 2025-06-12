package back.vybz.live_service.live.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddLiveVo {

    private String title;

    @Builder
    public RequestAddLiveVo(String title) {
        this.title = title;
    }

}
