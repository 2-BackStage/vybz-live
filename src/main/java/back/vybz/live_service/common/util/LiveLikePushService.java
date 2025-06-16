package back.vybz.live_service.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveLikePushService {

    private final ViewerWebSocketHandler viewerWebSocketHandler;
    private final LiveLikeRedisReader liveLikeRedisReader;

    public void pushLikeCount(String streamKey){
        Long likeCount = liveLikeRedisReader.getLikeCount(streamKey);
        System.out.println("👍 현재 Redis 좋아요 수: " + likeCount);
        viewerWebSocketHandler.pushLikeCount(streamKey, likeCount);
    }
}
