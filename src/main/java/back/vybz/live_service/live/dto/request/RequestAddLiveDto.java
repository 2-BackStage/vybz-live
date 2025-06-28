package back.vybz.live_service.live.dto.request;

import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import back.vybz.live_service.live.vo.request.RequestAddLiveVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class RequestAddLiveDto {

    private String title;
    private String buskerUuid;
    private String streamKey;
    private Long categoryId;
    private boolean membership;

    @Builder
    public RequestAddLiveDto(String title,
                             String buskerUuid,
                             String streamKey,
                             Long categoryId,
                             boolean membership) {
        this.title = title;
        this.buskerUuid = buskerUuid;
        this.streamKey = streamKey;
        this.categoryId = categoryId;
        this.membership = membership;
    }

    public static RequestAddLiveDto from(RequestAddLiveVo requestAddLiveVo, String buskerUuid, String streamKey) {
        return RequestAddLiveDto.builder()
                .title(requestAddLiveVo.getTitle())
                .buskerUuid(buskerUuid)
                .streamKey(streamKey)
                .categoryId(requestAddLiveVo.getCategoryId())
                .membership(requestAddLiveVo.isMembership())
                .build();
    }

    public LiveStream toEntity(){
        return LiveStream.builder()
                .buskerUuid(buskerUuid)
                .streamKey(streamKey)
                .title(title)
                .liveStreamStatus(LiveStreamStatus.ON_AIR)
                .likeCount(0)
                .viewerCount(0)
                .categoryId(categoryId)
                .membership(membership)
                .startTime(Instant.now())
                .build();
    }

}

