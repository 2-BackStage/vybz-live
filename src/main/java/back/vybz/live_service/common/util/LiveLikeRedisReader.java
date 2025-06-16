package back.vybz.live_service.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveLikeRedisReader {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String REDIS_LIKE_PREFIX = "live:like:";

    public Long getLikeCount(String streamKey){
        String value = stringRedisTemplate.opsForValue().get(REDIS_LIKE_PREFIX + streamKey);
        return value != null ? Long.parseLong(value) : 0L;
    }
}
