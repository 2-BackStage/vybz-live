package back.vybz.live_service.busker.domain.mongodb;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document("live_stream")
public class LiveStream {

    @Id
    private ObjectId id;

    // 버스커 uuid
    @Field(name = "user_uuid")
    private String userUuid;

    // 라이브 제목
    @Field(name = "title")
    private String title;

    // 좋아요 수
    @Field(name = "like_count")
    private Integer likeCount = 0;

    // live 시작 시간
    @Field(name = "started_at")
    private Instant startedAt;

    // live 종료 시간
    @Field(name = "ended_at")
    private Instant endedAt;

    // 생성 시간
    @Field(name = "created_at")
    private Instant createdAt;

    // 수정 시간
    @Field(name = "updated_at")
    private Instant updatedAt;

    @Builder
    public LiveStream(String userUuid, String title, Instant startedAt, Instant endedAt) {
        this.userUuid = userUuid;
        this.title = title;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

}
