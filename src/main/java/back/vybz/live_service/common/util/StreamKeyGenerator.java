package back.vybz.live_service.common.util;

import java.util.UUID;

public class StreamKeyGenerator {

    public static String generate() {
        return "stream-" + UUID.randomUUID().toString().replace("-", "");
    }
}
