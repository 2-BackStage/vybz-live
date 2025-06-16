package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.util.LiveLikePushService;
import back.vybz.live_service.common.util.LiveLikeRedisReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/live")
public class LiveLikeTestController {

    private final LiveLikeRedisReader liveLikeRedisReader;
    private final LiveLikePushService liveLikePushService;

    @GetMapping("/like-count/{streamKey}")
    public Long getLikeCount(@PathVariable String streamKey) {
        return liveLikeRedisReader.getLikeCount(streamKey);
    }

    @PostMapping("/{streamKey}/push-like")
    public void pushLike(@PathVariable String streamKey) {
        liveLikePushService.pushLikeCount(streamKey);
    }
}
