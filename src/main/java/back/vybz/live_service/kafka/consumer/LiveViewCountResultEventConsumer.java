package back.vybz.live_service.kafka.consumer;

import back.vybz.live_service.common.util.ViewerWebSocketHandler;
import back.vybz.live_service.kafka.event.LiveViewCountResultEvent;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LiveViewCountResultEventConsumer {

    private final ViewerWebSocketHandler viewerWebSocketHandler;
    private final LiveStreamRepository liveStreamRepository;

    @KafkaListener(
            topics = "live-view-count-result",
            groupId = "live-service-group",
            containerFactory = "liveViewCountResultEventKafkaTemplate"
    )
    public void consume(LiveViewCountResultEvent event) {
        String streamKey = event.getStreamKey();
        Integer viewerCount = event.getTotalViewerCount();

        log.info("📥 Kafka 수신: streamKey={}, totalViewerCount={}", streamKey, viewerCount);


        viewerWebSocketHandler.pushViewerCount(streamKey, viewerCount.longValue());
        log.info("📡 WebSocket push 완료: streamKey={}, totalViewerCount={}", streamKey, viewerCount);


        liveStreamRepository.findByStreamKey(streamKey)
                .ifPresentOrElse(stream -> {
                    stream.updateViewerCount(viewerCount);
                    liveStreamRepository.save(stream);
                    log.info("📝 live_stream.viewerCount 업데이트 완료: streamKey={}, viewerCount={}", streamKey, viewerCount);
                }, () -> {
                    log.warn("❗ live_stream 문서를 찾을 수 없음: streamKey={}", streamKey);
                });
    }
}

