package back.vybz.live_service.common.client;

import back.vybz.live_service.common.dto.SubscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "subscription-service",
    fallback = SubscriptionServiceClientFallback.class
)
public interface SubscriptionServiceClient {

    /**
     * 구독 정보 조회
     * @param buskerUuid 버스커 UUID
     * @param viewerUuid 시청자 UUID
     * @return 구독 정보
     */
    @GetMapping("/subscription-service/api/v1/subscriptions/{buskerUuid}/{viewerUuid}")
    SubscriptionResponse getSubscription(@PathVariable String buskerUuid, 
                                       @PathVariable String viewerUuid);

    /**
     * 구독 상태 확인 (간단한 버전)
     * @param buskerUuid 버스커 UUID
     * @param viewerUuid 시청자 UUID
     * @return 구독 여부
     */
    @GetMapping("/subscription-service/api/v1/subscriptions/{buskerUuid}/{viewerUuid}/status")
    boolean isSubscribed(@PathVariable String buskerUuid, 
                        @PathVariable String viewerUuid);
} 