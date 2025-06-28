package back.vybz.live_service.common.util;

import back.vybz.live_service.common.client.SupportServiceClient;
import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.infrastructure.LiveStreamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewerWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, List<WebSocketSession>> viewerSessions = new ConcurrentHashMap<>();
    private final LiveStreamRepository liveStreamRepository;
    private final SupportServiceClient supportServiceClient;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        String streamKey = extractStreamKey(webSocketSession);
        String viewerUuid = extractViewerUuid(webSocketSession);
        
        // 구독자 전용 라이브 검증
        if (!validateMembershipAccess(streamKey, viewerUuid)) {
            try {
                webSocketSession.sendMessage(new TextMessage("{\"type\": \"ERROR\", \"message\": \"구독자만 시청할 수 있는 라이브입니다.\"}"));
                webSocketSession.close(CloseStatus.POLICY_VIOLATION);
                log.warn("구독자 전용 라이브 접근 거부: streamKey={}, viewerUuid={}", streamKey, viewerUuid);
                return;
            } catch (Exception e) {
                log.error("WebSocket 에러 메시지 전송 실패", e);
            }
        }
        
        viewerSessions.computeIfAbsent(streamKey, k -> new ArrayList<>()).add(webSocketSession);
        log.info("👀 시청자 입장: streamKey={}, viewerUuid={}", streamKey, viewerUuid);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        String streamKey = extractStreamKey(webSocketSession);
        String viewerUuid = extractViewerUuid(webSocketSession);

        viewerSessions.values().forEach(list -> list.remove(webSocketSession));
        log.info("👋 시청자 퇴장: viewerUuid={}, streamKey={}", viewerUuid, streamKey);
    }

    public void notifyStreamEnded(String streamKey) {
        List<WebSocketSession> sessions = viewerSessions.getOrDefault(streamKey, List.of());
        List<WebSocketSession> sessionCopy = new ArrayList<>(sessions);

        log.info("📢 방송 종료 알림 시작 - streamKey: {}", streamKey);
        log.info("👀 연결된 시청자 수: {}", sessionCopy.size());

        for (WebSocketSession session : sessionCopy) {
            try {
                session.sendMessage(new TextMessage("스트림이 종료되었습니다."));
                session.close();
                log.info("✅ 종료 메시지 전송 성공: sessionId={}", session.getId());
            } catch (Exception e) {
                log.error("❌ WebSocket 메시지 전송 실패: {}", e.getMessage());
            }
        }

        viewerSessions.remove(streamKey);
    }

    public void pushLikeCount(String streamKey, Long likeCount) {
        List<WebSocketSession> sessions = viewerSessions.getOrDefault(streamKey, List.of());

        for (WebSocketSession session : sessions) {
            try {
                String json = String.format("{\"type\": \"LIKE_COUNT\", \"likeCount\": %d}", likeCount);
                session.sendMessage(new TextMessage(json));
                log.info("👍 좋아요 수 push: {} to session: {}", likeCount, session.getId());
            } catch (Exception e) {
                log.error("❌ 좋아요 WebSocket 메시지 실패: {}", e.getMessage());
            }
        }
    }

    public void pushViewerCount(String streamKey, Long viewerCount) {
        List<WebSocketSession> sessions = viewerSessions.getOrDefault(streamKey, List.of());

        for (WebSocketSession session : sessions) {
            try {
                String json = String.format("{\"type\": \"VIEWER_COUNT\", \"viewerCount\": %d}", viewerCount);
                session.sendMessage(new TextMessage(json));
                log.info("👀 시청자 수 push: {} to session: {}", viewerCount, session.getId());
            } catch (Exception e) {
                log.error("❌ 시청자 수 WebSocket 메시지 실패: {}", e.getMessage());
            }
        }
    }

    /**
     * 구독자 전용 라이브 접근 권한 검증
     * Support 서비스에서 구독 정보 조회
     */
    private boolean validateMembershipAccess(String streamKey, String viewerUuid) {
        try {
            LiveStream liveStream = liveStreamRepository.findByStreamKey(streamKey)
                    .orElse(null);
            
            if (liveStream == null) {
                log.warn("라이브 스트림을 찾을 수 없음: streamKey={}", streamKey);
                return false;
            }
            
            // 구독자 전용 라이브가 아닌 경우 접근 허용
            if (!liveStream.isMembership()) {
                return true;
            }
            
            // Support 서비스에서 구독 상태 조회
            boolean isSubscribed = supportServiceClient.checkSubscriptionStatus(
                    liveStream.getBuskerUuid(), viewerUuid);
            
            log.info("WebSocket 구독자 검증: streamKey={}, viewerUuid={}, isSubscribed={}", 
                    streamKey, viewerUuid, isSubscribed);
            
            return isSubscribed;
            
        } catch (Exception e) {
            log.error("WebSocket 구독자 검증 중 에러 발생: streamKey={}, viewerUuid={}", 
                    streamKey, viewerUuid, e);
            // 에러 발생 시 보안을 위해 접근 차단
            return false;
        }
    }

    private String extractStreamKey(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query == null) return null;
        for (String param : query.split("&")) {
            String[] kv = param.split("=");
            if (kv.length == 2 && kv[0].equals("streamKey")) return kv[1];
        }
        return null;
    }

    private String extractViewerUuid(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query == null) return null;
        for (String param : query.split("&")) {
            String[] kv = param.split("=");
            if (kv.length == 2 && kv[0].equals("viewerUuid")) return kv[1];
        }
        return null;
    }
}
