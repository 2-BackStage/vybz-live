package back.vybz.live_service.live.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document("live_stream")
public class LiveStream {

    @Id
    private String id;

    // 버스커 uuid
    private String buskerUuid;

    //스트리밍key
    private String streamKey;

    //방송제목
    private String title;

    //썸네일 URL
    private String thumbnailUrl;

    //방송 상태
    private LiveStreamStatus liveStreamStatus;

    //좋아요 수
    private int likeCount;

    //시청자 수
    private int viewerCount;

    //카테고리 ID
    private Long categoryId;

    //방송 시작 시간
    private Instant startTime;

    //방송 종료 시간
    private Instant endTime;

    //방송 생성 시간
    @CreatedDate
    private Instant createdAt;

    //방송 수정 시간
    @LastModifiedDate
    private Instant updatedAt;

    @Builder
    public LiveStream(String id,
                      String buskerUuid,
                      String streamKey,
                      String title,
                      String thumbnailUrl,
                      LiveStreamStatus liveStreamStatus,
                      int likeCount,
                      int viewerCount,
                      Long categoryId,
                      Instant startTime,
                      Instant endTime) {
        this.id = id;
        this.buskerUuid = buskerUuid;
        this.streamKey = streamKey;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.liveStreamStatus = liveStreamStatus;
        this.likeCount = likeCount;
        this.viewerCount = viewerCount;
        this.categoryId = categoryId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void endLiveStream() {
        this.liveStreamStatus = LiveStreamStatus.OFF_AIR;
        this.endTime = Instant.now();
    }

}
