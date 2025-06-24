package back.vybz.live_service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveViewCountResultEvent {

    private String streamKey;
    private Integer totalViewerCount;
}
