package back.vybz.live_service.kafka.config;

import back.vybz.live_service.kafka.event.LiveLikeCountResultEvent;
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
public class LiveLikeCountResultEventConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, LiveLikeCountResultEvent> liveLikeCountResultEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>(LiveLikeCountResultEvent.class, false)
                )
        );
    }

    @Bean(name = "liveLikeCountResultEventKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, LiveLikeCountResultEvent> liveLikeCountResultEventConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LiveLikeCountResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(liveLikeCountResultEventConsumerFactory());
        return factory;
    }
}
