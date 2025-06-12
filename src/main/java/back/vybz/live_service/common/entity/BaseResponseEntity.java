package back.vybz.live_service.common.entity;

import back.vybz.live_service.common.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record BaseResponseEntity<T>(
        @Schema(hidden = true) HttpStatusCode httpStatus,
        Boolean isSuccess,
        String message,
        int code,
        T result
) {

    /**
     * 1. Return 객체가 필요한 경우 -> 성공
     */
    public BaseResponseEntity(T result) {
        this(HttpStatus.OK, true, "SUCCESS", 200, result);
    }

    /**
     * 2. Return 객체가 필요 없는 경우 -> 성공
     */
    public BaseResponseEntity() {
        this(HttpStatus.OK, true, "SUCCESS", 200, null);
    }

    /**
     * 3. 요청에 실패한 경우 (에러 정보만 있음)
     */
    public BaseResponseEntity(BaseResponseStatus status) {
        this(status.getHttpStatusCode(), status.isSuccess(), status.getMessage(), status.getCode(), null);
    }

    /**
     * 4. 요청에 실패한 경우 + 커스텀 메시지
     */
    public BaseResponseEntity(BaseResponseStatus status, String message) {
        this(status.getHttpStatusCode(), status.isSuccess(), message, status.getCode(), null);
    }

    /**
     * 5. 성공이고, 커스텀 메시지 + 응답값
     */
    public BaseResponseEntity(BaseResponseStatus base, T result) {
        this(base.getHttpStatusCode(), base.isSuccess(), base.getMessage(), base.getCode(), result);
    }

    // ✅ 성공 응답 (기본 메시지: SUCCESS)
    public static <T> BaseResponseEntity<T> ok(T result) {
        return new BaseResponseEntity<>(result);
    }

    // ✅ 성공 응답 (커스텀 상태값 + result)
    public static <T> BaseResponseEntity<T> ok(BaseResponseStatus status, T result) {
        return new BaseResponseEntity<>(status, result);
    }

    // ✅ 실패 응답
    public static <T> BaseResponseEntity<T> fail(BaseResponseStatus status) {
        return new BaseResponseEntity<>(status);
    }

    // ✅ 실패 응답 (커스텀 메시지)
    public static <T> BaseResponseEntity<T> fail(BaseResponseStatus status, String message) {
        return new BaseResponseEntity<>(status, message);
    }

    // ✅ 실패 응답 (오류 데이터 포함) -- 이 메서드를 추가하는 것을 강력히 권장합니다!
    public static <T> BaseResponseEntity<T> failWithResult(BaseResponseStatus status, T result) {
        // 실패 상태이므로 isSuccess는 false여야 합니다.
        // BaseResponseStatus에서 isSuccess를 정확히 관리해야 합니다.
        return new BaseResponseEntity<>(status.getHttpStatusCode(), status.isSuccess(), status.getMessage(), status.getCode(), result);
    }
}
