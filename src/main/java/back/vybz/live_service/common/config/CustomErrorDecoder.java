package back.vybz.live_service.common.config;

import feign.FeignException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, feign.Response response) {
        log.error("Feign Client Error: methodKey={}, status={}", methodKey, response.status());
        
        switch (response.status()) {
            case 404:
                return new RuntimeException("구독 정보를 찾을 수 없습니다.");
            case 500:
                return new RuntimeException("구독 서비스 내부 오류가 발생했습니다.");
            default:
                return FeignException.errorStatus(methodKey, response);
        }
    }
} 