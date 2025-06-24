package back.vybz.live_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ViewCountKafkaEvent {

    private String streamKey;
    private String viewerUuid;

    @Builder
    public ViewCountKafkaEvent(String streamKey,
                               String viewerUuid) {
        this.streamKey = streamKey;
        this.viewerUuid = viewerUuid;
    }
}
