package back.vybz.live_service.kafka.consumer;

import back.vybz.live_service.common.util.ViewerWebSocketHandler;
import back.vybz.live_service.kafka.event.LiveLikeCountResultEvent;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiveLikeCountResultEventConsumer {

    private final ViewerWebSocketHandler viewerWebSocketHandler;
    private final LiveStreamRepository liveStreamRepository;

    @KafkaListener(
            topics = "live-like-count",
            groupId = "live-service-group",
            containerFactory = "liveLikeCountResultEventKafkaListenerContainerFactory"
    )
    public void consume(LiveLikeCountResultEvent event) {
        String streamKey = event.getStreamKey();
        Integer likeCount = event.getTotalLikeCount();

        log.info("📥 Kafka 수신: streamKey={}, totalLikeCount={}", streamKey, likeCount);

        liveStreamRepository.findByStreamKey(streamKey)
                .ifPresentOrElse(stream -> {

                    stream.updateLikeCount(likeCount);
                    liveStreamRepository.save(stream);
                    log.info("📝 live_stream.likeCount 업데이트 완료: streamKey={}, count={}", streamKey, likeCount);


                    viewerWebSocketHandler.pushLikeCount(streamKey, likeCount.longValue());
                    log.info("📡 WebSocket push 완료: streamKey={}, totalLikeCount={}", streamKey, likeCount);
                }, () -> {
                    log.warn("❗ live_stream 문서를 찾을 수 없음: streamKey={}", streamKey);
                });
    }
}