package back.vybz.live_service.live.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.*;
import java.util.List;

@Slf4j
@Service
public class FfmpegProcessService {

    private Process ffmpegProcess;
    private OutputStream ffmpegInput;

    private static final String RTMP_URL_PREFIX = "rtmp://localhost/live/";

    public void startFfmpeg(WebSocketSession session, String streamKey) throws IOException {
        String rtmpUrl = RTMP_URL_PREFIX + streamKey;

        List<String> command = List.of(
                "ffmpeg",
                "-f", "webm",
                "-i", "pipe:0",
                "-c:v", "libx264",
                "-preset", "veryfast",
                "-tune", "zerolatency",
                "-c:a", "aac",
                "-f", "flv",
                rtmpUrl
        );

        log.info("🚀 FFmpeg RTMP 송출 커맨드: {}", String.join(" ", command));

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(false);
        ffmpegProcess = builder.start();
        ffmpegInput = new BufferedOutputStream(ffmpegProcess.getOutputStream());

        log.info("✅ FFmpeg 프로세스 시작됨: streamKey={}, rtmpUrl={}", streamKey, rtmpUrl);


        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(ffmpegProcess.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.warn("🔥 [FFmpeg STDERR] {}", line);
                }
            } catch (IOException e) {
                log.error("❌ STDERR 로그 읽기 실패", e);
            }
        }, "ffmpeg-stderr-reader").start();
    }

    public void pushFrame(byte[] payload) throws IOException {
        if (ffmpegInput != null) {
            ffmpegInput.write(payload);
            ffmpegInput.flush();
            log.info("📥 [SERVER] Frame 전송 성공! size={} bytes", payload.length);
        } else {
            log.warn("⚠️ FFmpeg 입력 스트림이 null임 (종료됨)");
        }
    }

    public void stopFfmpeg() {
        if (ffmpegInput != null) {
            try {
                ffmpegInput.close();
            } catch (IOException ignored) {
            }
        }

        if (ffmpegProcess != null) {
            ffmpegProcess.destroy();
            log.info("🛑 FFmpeg 프로세스 종료됨");
        }
    }
}