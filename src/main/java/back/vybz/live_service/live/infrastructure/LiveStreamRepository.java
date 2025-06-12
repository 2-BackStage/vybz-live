package back.vybz.live_service.live.infrastructure;

import back.vybz.live_service.live.domain.LiveStream;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LiveStreamRepository extends MongoRepository<LiveStream,String> {
}
