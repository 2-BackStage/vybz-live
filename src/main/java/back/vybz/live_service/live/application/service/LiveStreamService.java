package back.vybz.live_service.live.application.service;

import back.vybz.live_service.live.dto.request.LiveStreamRequestDto;
import back.vybz.live_service.live.dto.request.RequestAddLiveDto;
import back.vybz.live_service.live.dto.request.ScrollLiveRequestDto;
import back.vybz.live_service.live.dto.response.LiveStreamResponseDto;
import back.vybz.live_service.live.dto.response.ResponseAddLiveDto;
import back.vybz.live_service.live.dto.response.ScrollLiveResponseDto;

public interface LiveStreamService {

    ResponseAddLiveDto createLiveStream(RequestAddLiveDto requestAddLiveDto, String buskerUuid);
    void endLiveStream(String buskerUuid, String streamKey);
    LiveStreamResponseDto getLiveStream(String streamKey, String viewerUuid);
    ScrollLiveResponseDto getLiveStreamScrollList(ScrollLiveRequestDto scrollLiveRequestDto);
    ScrollLiveResponseDto getLiveStreamScrollListByCategory(ScrollLiveRequestDto scrollLiveRequestDto);



}
