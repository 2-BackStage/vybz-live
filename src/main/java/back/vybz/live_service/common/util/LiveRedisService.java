package back.vybz.live_service.common.util;

import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LiveRedisService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String LIVE_NOW_KEY = "live:now";
    private static final String BUSKER_KEY_PREFIX = "live:busker:";

    public void saveLiveStreamToRedis(LiveStream liveStream){
        String buskerUuid = liveStream.getBuskerUuid();

        stringRedisTemplate.opsForSet().add(LIVE_NOW_KEY, buskerUuid);

        Map<String,String> streamInfo = new HashMap<>();
        streamInfo.put("streamKey", liveStream.getStreamKey());
        streamInfo.put("title", liveStream.getTitle());
        streamInfo.put("thumbnailUrl", liveStream.getThumbnailUrl() == null ? "" : liveStream.getThumbnailUrl());
        streamInfo.put("startedAt", liveStream.getStartTime().toString());
        streamInfo.put("status", liveStream.getLiveStreamStatus().name());

        stringRedisTemplate.opsForHash().putAll(BUSKER_KEY_PREFIX + buskerUuid, streamInfo);


    }
    public void removeLiveStreamFromRedis(String buskerUuid) {
        stringRedisTemplate.opsForSet().remove("live:now", buskerUuid);
        stringRedisTemplate.delete("live:busker:" + buskerUuid);
    }

    public boolean isViewerAlreadyWatching(String streamKey, String viewerUuid) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet()
                .isMember("viewer:set:" + streamKey, viewerUuid));
    }

    public void enterViewer(String streamKey, String viewerUuid) {
        Long added = stringRedisTemplate.opsForSet().add("viewer:set:" + streamKey, viewerUuid);
        if (added != null && added == 1L) {
            stringRedisTemplate.opsForValue().increment("viewer:count:" + streamKey);
        }
    }

    public int getViewerCount(String streamKey) {
        String count = stringRedisTemplate.opsForValue().get("viewer:count:" + streamKey);
        return count != null ? Integer.parseInt(count) : 0;
    }

    public Optional<LiveStream> getLiveStreamFromRedis(String streamKey) {

        Set<String> buskerUuids = stringRedisTemplate.opsForSet().members(LIVE_NOW_KEY);
        if (buskerUuids == null) return Optional.empty();

        for (String buskerUuid : buskerUuids) {
            Map<Object, Object> hash = stringRedisTemplate.opsForHash().entries(BUSKER_KEY_PREFIX + buskerUuid);
            if (hash != null && streamKey.equals(hash.get("streamKey"))) {
                return Optional.of(LiveStream.builder()
                        .buskerUuid(buskerUuid)
                        .streamKey((String) hash.get("streamKey"))
                        .title((String) hash.get("title"))
                        .thumbnailUrl((String) hash.get("thumbnailUrl"))
                        .startTime(Instant.parse((String) hash.get("startedAt")))
                        .liveStreamStatus(LiveStreamStatus.valueOf((String) hash.get("status")))
                        .build());
            }
        }
        return Optional.empty();
    }

    public void exitViewer(String streamKey, String viewerUuid) {
        Long removed = stringRedisTemplate.opsForSet()
                .remove("viewer:set:" + streamKey, viewerUuid);

        if (removed != null && removed == 1L) {
            stringRedisTemplate.opsForValue().decrement("viewer:count:" + streamKey);
            System.out.println("👋 시청자 퇴장 처리 완료: " + viewerUuid);
        } else {
            System.out.println("⚠️ 퇴장 처리 대상 없음 또는 중복 퇴장: " + viewerUuid);
        }
    }



}
