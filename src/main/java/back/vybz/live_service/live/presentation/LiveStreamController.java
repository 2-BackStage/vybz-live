package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import back.vybz.live_service.live.application.service.LiveStreamService;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import back.vybz.live_service.live.vo.response.ResponseAddLiveVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/live")
@RequiredArgsConstructor
public class LiveStreamController {

    private final LiveStreamService liveStreamService;

    @Operation(
            summary = "라이브 스트림 시작 API",
            description = "라이브 스트림을 시작하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @PostMapping("/start")
    public BaseResponseEntity<ResponseAddLiveVo> createLiveStream(HttpServletRequest httpServletRequest,
                                                                  @RequestBody RequestAddLiveVo requestAddLiveVo) {
        String buskerUuid = httpServletRequest.getHeader("X-Busker-Id");
        RequestAddLiveDto requestAddLiveDto = RequestAddLiveDto.from(requestAddLiveVo, buskerUuid, null);
        ResponseAddLiveDto responseAddLiveDto = liveStreamService.createLiveStream(requestAddLiveDto, buskerUuid);
        return new BaseResponseEntity<>(responseAddLiveDto.toVo());
    }

}
