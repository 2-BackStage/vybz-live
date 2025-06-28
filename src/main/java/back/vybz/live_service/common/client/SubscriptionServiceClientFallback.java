package back.vybz.live_service.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionServiceClientFallback implements SubscriptionServiceClient {

    @Override
    public boolean checkSubscriptionStatus(String buskerUuid, String viewerUuid) {
        log.warn("Subscription 서비스 연결 실패 - Fallback 실행: buskerUuid={}, viewerUuid={}", buskerUuid, viewerUuid);
        
        // Fallback 정책: 서비스 다운 시 접근 차단 (보안 우선)
        return false;
    }
} 