package back.vybz.live_service.live.infrastructure;

import back.vybz.live_service.live.domain.LiveStream;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LiveStreamRepository extends MongoRepository<LiveStream,String>, LiveStreamRepositoryCustom {

    Optional<LiveStream> findByBuskerUuidAndStreamKey(String buskerUuid, String streamKey);
}
