package back.vybz.live_service.live.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamRequestDto {

    private String streamKey;
    private String viewerUuid;

    @Builder
    public LiveStreamRequestDto(String streamKey,
                                String viewerUuid) {
        this.streamKey = streamKey;
        this.viewerUuid = viewerUuid;
    }

    public LiveStreamRequestDto from(String streamKey, String viewerUuid) {
        return LiveStreamRequestDto.builder()
                .streamKey(streamKey)
                .viewerUuid(viewerUuid)
                .build();
    }

}
