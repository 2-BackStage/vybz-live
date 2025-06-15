package back.vybz.live_service.live.application.service;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import back.vybz.live_service.live.dto.response.BuskerCategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "busker-info-service")
public interface BuskerFeignClient {

    @GetMapping("/api/v1/busker-category/{buskerUuid}/category")
    BaseResponseEntity<BuskerCategoryResponseDto> getMainCategoryByBusker(@PathVariable("buskerUuid") String buskerUuid);
}

