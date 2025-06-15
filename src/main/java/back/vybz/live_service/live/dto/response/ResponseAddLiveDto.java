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
    private String hlsUrl;
    private LiveStreamStatus liveStreamStatus;
    private Long categoryId;

    @Builder
    public ResponseAddLiveDto(String id,
                              String streamKey,
                              String hlsUrl,
                              LiveStreamStatus liveStreamStatus,
                              Long categoryId) {
        this.id = id;
        this.streamKey = streamKey;
        this.hlsUrl = hlsUrl;
        this.liveStreamStatus = liveStreamStatus;
        this.categoryId = categoryId;
    }
    public ResponseAddLiveVo toVo() {
        return ResponseAddLiveVo.builder()
                .id(id)
                .streamKey(streamKey)
                .hlsUrl(hlsUrl)
                .liveStreamStatus(liveStreamStatus)
                .categoryId(categoryId)
                .build();
    }

}
