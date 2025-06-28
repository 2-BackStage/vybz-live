package back.vybz.live_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    
    private String subscriptionId;
    private String buskerUuid;
    private String subscriberUuid;
    private SubscriptionStatus status;
    private SubscriptionType type;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;
    
    public enum SubscriptionStatus {
        ACTIVE,     // 활성
        EXPIRED,    // 만료
        CANCELLED,  // 취소
        PENDING     // 대기
    }
    
    public enum SubscriptionType {
        MONTHLY,    // 월간
        YEARLY,     // 연간
        LIFETIME    // 평생
    }
    
    /**
     * 구독이 유효한지 확인
     */
    public boolean isValid() {
        return isActive && 
               status == SubscriptionStatus.ACTIVE && 
               (endDate == null || endDate.isAfter(Instant.now()));
    }
} 