package back.vybz.live_service.kafka.producer;

import back.vybz.live_service.kafka.event.ViewCountKafkaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewCountKafkaEventProducer {

    private final KafkaTemplate<String, ViewCountKafkaEvent> viewCountKafkaTemplate;
    private static final String TOPIC_NAME = "live-view-count";

    public void send(ViewCountKafkaEvent event){
        log.info("[Kafka] sending ViewCountKafkaEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, ViewCountKafkaEvent>> future =
                viewCountKafkaTemplate.send(TOPIC_NAME, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send ViewCountKafkaEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent ViewCountKafkaEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
