package back.vybz.live_service.common.util;

import back.vybz.live_service.live.application.service.FfmpegProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;

@Component
@RequiredArgsConstructor
public class StreamWebSocketHandler extends BinaryWebSocketHandler {

    private final FfmpegProcessService ffmpegProcessService;
    private final ViewerWebSocketHandler viewerWebSocketHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            String streamKey = getStreamKeyFromQuery(session);

            ffmpegProcessService.startFfmpeg(session, streamKey);
            System.out.println("✅ WebSocket 연결 성공 및 FFmpeg 프로세스 시작됨. streamKey = " + streamKey);
        } catch (Exception e) {
            System.err.println("❌ WebSocket 연결 중 에러: " + e.getMessage());
            e.printStackTrace();
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        System.out.println("📥 [SERVER] WebSocket Frame Received! Size: " + message.getPayloadLength());

        ByteBuffer buffer = message.getPayload();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        ffmpegProcessService.pushFrame(bytes);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("🛑 [SERVER] WebSocket 연결 종료: " + status);
        ffmpegProcessService.stopFfmpeg();

        String streamKey = getStreamKeyFromQuery(session);
        viewerWebSocketHandler.notifyStreamEnded(streamKey);
    }

    private String getStreamKeyFromQuery(WebSocketSession webSocketSession) {
        String query = webSocketSession.getUri().getQuery();
        if (query == null) {
            return "defaultStreamKey";
        }

        for (String param : query.split("&")) {
            String[] kv = param.split("=");
            if (kv.length == 2 && kv[0].equals("streamKey")) {
                return kv[1];
            }
        }
        return "defaultStreamKey";
    }
}
