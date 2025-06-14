package back.vybz.live_service.live.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterLiveStreamRequestDto {

    private String streamKey;
    private String viewerUuid;

    @Builder
    public EnterLiveStreamRequestDto(String streamKey,
                                     String viewerUuid) {
        this.streamKey = streamKey;
        this.viewerUuid = viewerUuid;
    }

    public EnterLiveStreamRequestDto from(String streamKey, String viewerUuid) {
        return EnterLiveStreamRequestDto.builder()
                .streamKey(streamKey)
                .viewerUuid(viewerUuid)
                .build();
    }

}
