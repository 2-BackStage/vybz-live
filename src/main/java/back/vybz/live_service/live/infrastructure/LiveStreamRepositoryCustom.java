package back.vybz.live_service.live.infrastructure;

import back.vybz.live_service.live.domain.LiveStream;

import java.util.List;

public interface LiveStreamRepositoryCustom  {

    List<LiveStream> findLiveStreamAllWithScroll(String lastId, int size);
    List<LiveStream> findLiveStreamWithScroll(String lastId, Long categoryId, int size);
}
