package back.vybz.live_service.live.vo.response;

import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ScrollLiveResponseVo {
    private String streamKey;
    private String title;
    private String buskerUuid;
    private String thumbnailUrl;
    private int viewerCount;
    private LiveStreamStatus liveStreamStatus;
    private Instant startedAt;
    private boolean membership;

    @Builder
    public ScrollLiveResponseVo(String streamKey,
                                String title,
                                String buskerUuid,
                                String thumbnailUrl,
                                int viewerCount,
                                LiveStreamStatus liveStreamStatus,
                                Instant startedAt,
                                boolean membership) {
        this.streamKey = streamKey;
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.viewerCount = viewerCount;
        this.liveStreamStatus = liveStreamStatus;
        this.startedAt = startedAt;
        this.membership = membership;
    }

    public static ScrollLiveResponseVo from(LiveStream liveStream) {
        return ScrollLiveResponseVo.builder()
                .streamKey(liveStream.getStreamKey())
                .title(liveStream.getTitle())
                .buskerUuid(liveStream.getBuskerUuid())
                .thumbnailUrl(liveStream.getThumbnailUrl())
                .viewerCount(liveStream.getViewerCount())
                .liveStreamStatus(liveStream.getLiveStreamStatus())
                .startedAt(liveStream.getStartTime())
                .membership(liveStream.isMembership())
                .build();
    }
    public static List<ScrollLiveResponseVo> listFrom(List<LiveStream> liveStreams) {
        return liveStreams.stream()
                .map(ScrollLiveResponseVo::from)
                .collect(Collectors.toList());
    }
}
