package back.vybz.live_service.live.infrastructure;

import back.vybz.live_service.live.domain.LiveStream;
import back.vybz.live_service.live.domain.LiveStreamStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LiveStreamRepositoryCustomImpl implements LiveStreamRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<LiveStream> findLiveStreamAllWithScroll(String lastId, int size) {
        Query query = new Query();

        if (lastId != null && !lastId.isBlank()) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }

        query.addCriteria(Criteria.where("liveStreamStatus").is(LiveStreamStatus.ON_AIR));
        query.with(Sort.by(Sort.Order.desc("_id")));
        query.limit(size + 1);

        return mongoTemplate.find(query, LiveStream.class);
    }

    @Override
    public List<LiveStream> findLiveStreamWithScroll(String lastId, Long categoryId, int size) {
        Query query = new Query();

        if (lastId != null && !lastId.isBlank()) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }

        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("liveStreamStatus").is(LiveStreamStatus.ON_AIR),
                        Criteria.where("categoryId").is(categoryId)
                )
        );

        query.with(Sort.by(Sort.Order.desc("_id")));
        query.limit(size + 1);

        return mongoTemplate.find(query, LiveStream.class);
    }
}
