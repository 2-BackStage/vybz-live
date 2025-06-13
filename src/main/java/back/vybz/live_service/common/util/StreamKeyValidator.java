package back.vybz.live_service.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StreamKeyValidator {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String BUSKER_KEY_PREFIX = "live:busker:";

    public boolean isValidStreamKey(String streamKey) {
        Set<String> keys = stringRedisTemplate.keys("live:busker:*");

        if (keys == null || keys.isEmpty()) {
            System.out.println("🔴 Redis에 live:busker:* 키 없음");
            return false;
        }

        for (String key : keys) {
            Object value = stringRedisTemplate.opsForHash().get(key, "streamKey");
            System.out.println("🔍 비교중: streamKey=" + streamKey + " vs Redis[" + key + "]= " + value);
            if (value != null && streamKey.equals(value.toString())) {
                System.out.println("✅ streamKey 인증 통과!");
                return true;
            }
        }

        System.out.println("❌ streamKey 인증 실패: " + streamKey);
        return false;
    }


}
