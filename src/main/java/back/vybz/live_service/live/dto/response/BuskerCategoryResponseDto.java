package back.vybz.live_service.live.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuskerCategoryResponseDto {

    private String buskerUuid;
    private Long categoryId;

    @Builder
    public BuskerCategoryResponseDto(String buskerUuid,
                                     Long categoryId) {
        this.buskerUuid = buskerUuid;
        this.categoryId = categoryId;
    }
}
