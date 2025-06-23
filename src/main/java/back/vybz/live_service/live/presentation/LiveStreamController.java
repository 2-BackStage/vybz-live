package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import back.vybz.live_service.live.application.service.LiveStreamService;
import back.vybz.live_service.live.dto.request.LiveStreamRequestDto;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.request.ScrollLiveRequestDto;
import back.vybz.live_service.live.dto.response.LiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.dto.response.ScrollLiveResponseDto;
import back.vybz.live_service.live.vo.request.LiveStreamRequestVo;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import back.vybz.live_service.live.vo.response.LiveStreamResponseVo;
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
    @GetMapping("/enter/{streamKey}")
    public BaseResponseEntity<LiveStreamResponseVo> enterLiveStream(HttpServletRequest httpServletRequest,
                                                                    @PathVariable("streamKey") String streamKey) {
        String viewerUuid = httpServletRequest.getHeader("X-User-Id");
        LiveStreamResponseDto liveStreamResponseDto = liveStreamService.getLiveStream(streamKey, viewerUuid);
        return new BaseResponseEntity<>(liveStreamResponseDto.toVo());
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


    @Operation(
            summary = "라이브 방송 목록 무한스크롤 조회 API",
            description = "라이브 방송 목록을 최신순으로 무한스크롤 방식으로 조회합니다. " +
                    "size는 한 페이지에 가져올 개수이며, 다음 목록 요청 시에는 lastId에 이전 목록의 마지막 id를 넣어주세요.",
            tags = {"LIVE-SERVICE"}
    )
    @GetMapping("/all")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamList(@RequestParam (required = false) String lastId,
                                                                             @RequestParam (defaultValue = "10") int size) {
        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollList(scrollLiveRequestDto));
    }


    @Operation(
            summary = "카테고리별 라이브 방송 목록 무한스크롤 조회 API",
            description = "특정 카테고리의 라이브 방송 목록을 무한스크롤 방식으로 조회합니다. " +
                    "size는 한 페이지에 가져올 개수이며, 다음 목록 요청 시에는 lastId에 이전 목록의 마지막 id를 넣어주세요.",
            tags = {"LIVE-SERVICE"}
    )
    @GetMapping("/category")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamListByCategory(@RequestParam Long categoryId,
                                                                                       @RequestParam(required = false) String lastId,
                                                                                       @RequestParam(defaultValue = "10") int size) {
        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .categoryId(categoryId)
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollListByCategory(scrollLiveRequestDto));
    }

}
