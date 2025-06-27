package back.vybz.live_service.live.presentation;

import back.vybz.live_service.common.entity.BaseResponseEntity;
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

    @Operation(
            summary = "라이브 스트림 시작 API",
            description = "라이브 스트림을 시작하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @PostMapping("/start")
    public BaseResponseEntity<ResponseAddLiveVo> createLiveStream(
            @RequestHeader("X-Busker-Id") String buskerUuid,
            @RequestBody RequestAddLiveVo requestAddLiveVo) {

        log.info("🎬 [start] 컨트롤러 진입");
        log.info("📌 X-Busker-Id: {}", buskerUuid);
        log.info("📦 RequestAddLiveVo: {}", requestAddLiveVo);

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
    public BaseResponseEntity<Void> endLiveStream(
            @RequestHeader("X-Busker-Id") String buskerUuid,
            @RequestParam("streamKey") String streamKey) {

        log.info("🛑 [end] 컨트롤러 진입");
        log.info("📌 X-Busker-Id: {}", buskerUuid);
        log.info("📌 streamKey: {}", streamKey);

        liveStreamService.endLiveStream(buskerUuid, streamKey);
        return new BaseResponseEntity<>();
    }

    @Operation(
            summary = "라이브 스트림 입장 API",
            description = "라이브 스트림에 입장하는 API입니다.",
            tags = {"LIVE-SERVICE"}
    )
    @GetMapping("/enter/{streamKey}")
    public BaseResponseEntity<LiveStreamResponseVo> enterLiveStream(
            @RequestHeader("X-User-Id") String viewerUuid,
            @PathVariable("streamKey") String streamKey) {

        log.info("🚪 [enter] 컨트롤러 진입");
        log.info("📌 streamKey: {}", streamKey);
        log.info("📌 X-User-Id: {}", viewerUuid);

        LiveStreamResponseDto liveStreamResponseDto = liveStreamService.getLiveStream(streamKey, viewerUuid);
        return new BaseResponseEntity<>(liveStreamResponseDto.toVo());
    }

    @Operation(
            summary = "라이브 방송 목록 무한스크롤 조회 API",
            description = "라이브 방송 목록을 최신순으로 무한스크롤 방식으로 조회합니다.",
            tags = {"LIVE-SERVICE"}
    )
    @GetMapping("/all")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamList(
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "10") int size) {

        log.info("📃 [all] 컨트롤러 진입");
        log.info("📌 lastId: {}", lastId);
        log.info("📌 size: {}", size);

        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollList(scrollLiveRequestDto));
    }

    @Operation(
            summary = "카테고리별 라이브 방송 목록 무한스크롤 조회 API",
            description = "특정 카테고리의 라이브 방송 목록을 무한스크롤 방식으로 조회합니다.",
            tags = {"LIVE-SERVICE"}
    )
    @GetMapping("/category")
    public BaseResponseEntity<ScrollLiveResponseDto> getScrollLiveStreamListByCategory(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "10") int size) {

        log.info("📂 [category] 컨트롤러 진입");
        log.info("📌 categoryId: {}", categoryId);
        log.info("📌 lastId: {}", lastId);
        log.info("📌 size: {}", size);

        ScrollLiveRequestDto scrollLiveRequestDto = ScrollLiveRequestDto.builder()
                .categoryId(categoryId)
                .lastId(lastId)
                .size(size)
                .build();

        return BaseResponseEntity.ok(liveStreamService.getLiveStreamScrollListByCategory(scrollLiveRequestDto));
    }
}
