package back.vybz.live_service.kafka.config;

import back.vybz.live_service.kafka.event.ViewCountKafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class ViewCountKafkaEventConfig {

    private final  CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ProducerFactory<String, ViewCountKafkaEvent> viewCountKafkaEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonKafkaConfig.commonProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, ViewCountKafkaEvent> viewCountKafkaEventKafkaTemplate() {
        return new KafkaTemplate<>(viewCountKafkaEventProducerFactory());
    }
}
