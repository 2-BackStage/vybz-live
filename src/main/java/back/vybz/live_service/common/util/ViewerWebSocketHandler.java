package back.vybz.live_service.common.util;

import back.vybz.live_service.live.application.service.LiveStreamService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ViewerWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, List<WebSocketSession>> viewerSessions = new ConcurrentHashMap<>();
    private final LiveLikeRedisReader liveLikeRedisReader;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        String streamKey = extractStreamKey(webSocketSession);
        viewerSessions.computeIfAbsent(streamKey, k -> new ArrayList<>()).add(webSocketSession);
        System.out.println("👀 시청자 입장: " + streamKey);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        String streamKey = extractStreamKey(webSocketSession);
        String viewerUuid = extractViewerUuid(webSocketSession);

        viewerSessions.values().forEach(list -> list.remove(webSocketSession));
        System.out.println("👋 시청자 퇴장: " + viewerUuid + " from " + streamKey);

    }


    public void notifyStreamEnded(String streamKey) {
        List<WebSocketSession> sessions = viewerSessions.getOrDefault(streamKey, List.of());

        List<WebSocketSession> sessionCopy = new ArrayList<>(sessions);

        System.out.println("📢 방송 종료 알림 시작 - streamKey: " + streamKey);
        System.out.println("👀 연결된 시청자 수: " + sessionCopy.size());

        for (WebSocketSession session : sessionCopy) {
            try {
                session.sendMessage(new TextMessage("스트림이 종료되었습니다."));
                session.close();
                System.out.println("✅ 종료 메시지 전송 성공: sessionId=" + session.getId());
            } catch (Exception e) {
                System.err.println("❌ WebSocket 메시지 전송 실패: " + e.getMessage());
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
                System.out.println("👍 좋아요 수 push: " + likeCount + " to session: " + session.getId());
            } catch (Exception e) {
                System.err.println("❌ 좋아요 WebSocket 메시지 실패: " + e.getMessage());
            }
        }
    }

    @PostConstruct
    public void startLikeBroadcastScheduler() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::broadcastLikesToAllViewers, 0, 3, TimeUnit.SECONDS);
    }

    private void broadcastLikesToAllViewers() {
        for (Map.Entry<String, List<WebSocketSession>> entry : viewerSessions.entrySet()) {
            String streamKey = entry.getKey();
            List<WebSocketSession> sessions = entry.getValue();

            long likeCount = liveLikeRedisReader.getLikeCount(streamKey);

            List<WebSocketSession> copy = new ArrayList<>(sessions);
            for (WebSocketSession session : copy) {
                try {
                    session.sendMessage(new TextMessage("likeCount:" + likeCount));
                } catch (Exception e) {
                    System.err.println("❌ 좋아요 전송 실패: " + e.getMessage());
                }
            }
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
