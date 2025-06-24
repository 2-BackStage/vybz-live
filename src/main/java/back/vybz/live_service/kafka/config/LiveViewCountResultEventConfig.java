package back.vybz.live_service.kafka.config;

import back.vybz.live_service.kafka.event.LiveViewCountResultEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class LiveViewCountResultEventConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, LiveViewCountResultEvent> liveViewCountResultEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>(LiveViewCountResultEvent.class,false)
                )
        );
    }

    @Bean(name = "liveViewCountResultEventKafkaTemplate")
    public ConcurrentKafkaListenerContainerFactory<String, LiveViewCountResultEvent> liveViewCountResultEventConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, LiveViewCountResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(liveViewCountResultEventConsumerFactory());
        return factory;
    }
}
