package back.vybz.live_service.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "support-service",
    fallback = SupportServiceClientFallback.class
)
public interface SupportServiceClient {

    /**
     * 구독 상황 조회
     * @param buskerUuid 버스커 UUID
     * @param viewerUuid 시청자 UUID
     * @return 구독 여부 (true: 구독 중, false: 구독하지 않음)
     */
    @GetMapping("/support-service/api/v1/subscriptions/{buskerUuid}/{viewerUuid}/status")
    boolean checkSubscriptionStatus(@PathVariable String buskerUuid, 
                                   @PathVariable String viewerUuid);
} 