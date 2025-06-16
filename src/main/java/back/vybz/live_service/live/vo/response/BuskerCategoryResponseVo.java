package back.vybz.live_service.live.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuskerCategoryResponseVo {

    private String buskerUuid;
    private Long categoryId;

    @Builder
    public BuskerCategoryResponseVo(String buskerUuid,
                                    Long categoryId) {
        this.buskerUuid = buskerUuid;
        this.categoryId = categoryId;
    }
}
