package back.vybz.live_service.live.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiveStreamStatus {
    ON_AIR("방송 중"),
    OFF_AIR("방송 종료"),
    PREPARING("방송 준비 중");

    private final String description;
}
