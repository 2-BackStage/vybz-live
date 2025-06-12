package back.vybz.live_service.common.exception;

import back.vybz.live_service.common.entity.BaseResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {


    /**
     * 발생한 예외 처리
     */

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> BaseError(BaseException e) {
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(e.getStatus());
        log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> RuntimeError(RuntimeException e) {
        BaseResponseEntity<Void> response = new BaseResponseEntity<>(BaseResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error("RuntimeException: ", e);
        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponseEntity<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        // Map<String, String> 타입으로 변경하여 여러 필드 오류를 담을 수 있도록 합니다.
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("Validation failed for fields: {}", errors);

        // BaseResponseEntity의 제네릭 타입을 Map<String, String>으로 변경하고,
        // data 필드에 errors 맵을 담습니다.
        BaseResponseEntity<Map<String, String>> response = new BaseResponseEntity<>(
                BaseResponseStatus.INVALID_REQUEST.getHttpStatusCode(),
                false,
                "요청 유효성 검증에 실패했습니다.", // 포괄적인 메시지 (옵션)
                BaseResponseStatus.INVALID_REQUEST.getCode(),
                errors // 여기에 필드별 오류 맵을 전달
        );
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<BaseResponseEntity<Void>> handleJsonParseException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        if (cause != null && cause.getMessage() != null && cause.getMessage().contains("java.time.LocalDate")) {
            String errorMessage = "생년월일은 YYYY-mm-dd 형식이어야 합니다.";
            log.warn("LocalDate parsing failed: {}", cause.getMessage());
            return new ResponseEntity<>(
                    new BaseResponseEntity<>(
                            BaseResponseStatus.INVALID_REQUEST.getHttpStatusCode(),
                            false,
                            errorMessage,
                            BaseResponseStatus.INVALID_REQUEST.getCode(),
                            null
                    ),
                    BaseResponseStatus.INVALID_REQUEST.getHttpStatusCode()
            );
        }

        return new ResponseEntity<>(
                new BaseResponseEntity<>(
                        BaseResponseStatus.INVALID_REQUEST.getHttpStatusCode(),
                        false,
                        "요청 형식이 올바르지 않습니다.",
                        BaseResponseStatus.INVALID_REQUEST.getCode(),
                        null
                ),
                BaseResponseStatus.INVALID_REQUEST.getHttpStatusCode()
        );
    }

}

