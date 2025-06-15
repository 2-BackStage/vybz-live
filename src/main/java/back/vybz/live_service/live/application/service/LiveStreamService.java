package back.vybz.live_service.live.application.service;

import back.vybz.live_service.live.dto.request.EnterLiveStreamRequestDto;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.response.EnterLiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;

public interface LiveStreamService {

    ResponseAddLiveDto createLiveStream(RequestAddLiveDto requestAddLiveDto, String buskerUuid);
    void endLiveStream(String buskerUuid, String streamKey);
    EnterLiveStreamResponseDto enterLiveStream(EnterLiveStreamRequestDto enterLiveStreamRequestDto, String viewerUuid);
    void existLiveStream(String streamKey, String viewerUuid);


}
