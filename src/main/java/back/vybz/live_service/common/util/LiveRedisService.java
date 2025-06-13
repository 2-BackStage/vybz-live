package back.vybz.live_service.common.util;

import back.vybz.live_service.live.domain.LiveStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
}
