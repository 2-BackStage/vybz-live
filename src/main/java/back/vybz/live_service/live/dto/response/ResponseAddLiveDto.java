package back.vybz.live_service.live.dto.response;

import back.vybz.live_service.live.domain.LiveStreamStatus;
import back.vybz.live_service.live.vo.response.ResponseAddLiveVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseAddLiveDto {
    private String id;
    private String streamKey;
    private LiveStreamStatus liveStreamStatus;
    private Long categoryId;
    private boolean membership;

    @Builder
    public ResponseAddLiveDto(String id,
                              String streamKey,
                              LiveStreamStatus liveStreamStatus,
                              Long categoryId,
                              boolean membership) {
        this.id = id;
        this.streamKey = streamKey;
        this.liveStreamStatus = liveStreamStatus;
        this.categoryId = categoryId;
        this.membership = membership;
    }

    public ResponseAddLiveVo toVo() {
        return ResponseAddLiveVo.builder()
                .id(id)
                .streamKey(streamKey)
                .liveStreamStatus(liveStreamStatus)
                .categoryId(categoryId)
                .membership(membership)
                .build();
    }
}

