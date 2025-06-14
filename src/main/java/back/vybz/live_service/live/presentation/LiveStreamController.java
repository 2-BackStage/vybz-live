package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import back.vybz.live_service.live.application.service.LiveStreamService;
import back.vybz.live_service.live.dto.request.EnterLiveStreamRequestDto;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.response.EnterLiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.vo.request.EnterLiveStreamRequestVo;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import back.vybz.live_service.live.vo.response.EnterLiveStreamResponseVo;
import back.vybz.live_service.live.vo.response.ResponseAddLiveVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "라이브 스트림 종료 API",
            description = "라이브 스트림을 종료하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @PostMapping("/end")
    public BaseResponseEntity<Void> endLiveStream(HttpServletRequest httpServletRequest,
                                                  @RequestParam("streamKey") String streamKey) {
        String buskerUuid = httpServletRequest.getHeader("X-Busker-Id");
        liveStreamService.endLiveStream(buskerUuid, streamKey);
        return new BaseResponseEntity<>();
    }

    @Operation(
            summary = "라이브 스트림 입장 API",
            description = "라이브 스트림에 입장하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @PostMapping("/enter")
    public BaseResponseEntity<EnterLiveStreamResponseVo> enterLiveStream(HttpServletRequest httpServletRequest,
                                                                         @RequestBody EnterLiveStreamRequestVo enterLiveStreamRequestVo) {
        String viewerUuid = httpServletRequest.getHeader("X-User-Id");
        EnterLiveStreamRequestDto enterLiveStreamRequestDto = new EnterLiveStreamRequestDto(enterLiveStreamRequestVo.getStreamKey(), viewerUuid);
        EnterLiveStreamResponseDto enterLiveStreamResponseDto = liveStreamService.enterLiveStream(enterLiveStreamRequestDto, viewerUuid);
        return new BaseResponseEntity<>(enterLiveStreamResponseDto.toVo());
    }

    @Operation(
            summary = "라이브 스트림 퇴장 API",
            description = "라이브 스트림에서 퇴장하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @PostMapping("/exit")
    public BaseResponseEntity<Void> exitLiveStream(HttpServletRequest httpServletRequest,
                                                   @RequestParam("streamKey") String streamKey) {
        String viewerUuid = httpServletRequest.getHeader("X-User-Id");
        liveStreamService.existLiveStream(streamKey, viewerUuid);
        return new BaseResponseEntity<>();
    }
}
