package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import back.vybz.live_service.common.exception.BaseException;
import back.vybz.live_service.common.exception.BaseResponseStatus;
import back.vybz.live_service.live.application.service.LiveStreamService;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.request.ScrollLiveRequestDto;
import back.vybz.live_service.live.dto.response.LiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.dto.response.ScrollLiveResponseDto;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import back.vybz.live_service.live.vo.response.LiveStreamResponseVo;
import back.vybz.live_service.live.vo.response.ResponseAddLiveVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/live")
@RequiredArgsConstructor
public class LiveStreamController {

    private final LiveStreamService liveStreamService;

    @Operation(summary = "라이브 스트림 시작 API", description = "버스커만 사용 가능", tags = {"LIVE-SERVICE"})
    @PostMapping("/start")
    public BaseResponseEntity<ResponseAddLiveVo> createLiveStream(
            @RequestBody RequestAddLiveVo requestAddLiveVo) {

        log.info("🎬 [start] 컨트롤러 진입");
        log.info("📦 RequestAddLiveVo: {}", requestAddLiveVo);

        RequestAddLiveDto requestAddLiveDto = RequestAddLiveDto.from(requestAddLiveVo, null, null);
        ResponseAddLiveDto responseAddLiveDto = liveStreamService.createLiveStream(requestAddLiveDto, null);
        return new BaseResponseEntity<>(responseAddLiveDto.toVo());
    }

    @Operation(summary = "라이브 스트림 종료 API", description = "버스커만 사용 가능", tags = {"LIVE-SERVICE"})
    @PostMapping("/end")
    public BaseResponseEntity<Void> endLiveStream(
            @RequestParam("streamKey") String streamKey) {

        log.info("🛑 [end] 컨트롤러 진입 - streamKey: {}", streamKey);

        liveStreamService.endLiveStream(null, streamKey);
        return new BaseResponseEntity<>();
    }

    @Operation(summary = "라이브 스트림 입장 API", description = "유저/버스커 모두 입장 가능", tags = {"LIVE-SERVICE"})
    @GetMapping("/enter/{streamKey}")
    public BaseResponseEntity<LiveStreamResponseVo> enterLiveStream(
            @PathVariable("streamKey") String streamKey) {

        log.info("🚪 [enter] 컨트롤러 진입 - streamKey: {}", streamKey);

        LiveStreamResponseDto liveStreamResponseDto = liveStreamService.getLiveStream(streamKey, null);
        return new BaseResponseEntity<>(liveStreamResponseDto.toVo());
    }

    @Operation(summary = "라이브 방송 목록 무한스크롤 조회 API", tags = {"LIVE-SERVICE"})
    @GetMapping("/all")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamList(
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "10") int size) {

        log.info("📃 [all] 컨트롤러 진입 - lastId: {}, size: {}", lastId, size);

        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollList(scrollLiveRequestDto));
    }

    @Operation(summary = "카테고리별 라이브 방송 목록 무한스크롤 조회 API", tags = {"LIVE-SERVICE"})
    @GetMapping("/category")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamListByCategory(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "10") int size) {

        log.info("📂 [category] 컨트롤러 진입 - categoryId: {}, lastId: {}, size: {}", categoryId, lastId, size);

        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .categoryId(categoryId)
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollListByCategory(scrollLiveRequestDto));
    }
}
