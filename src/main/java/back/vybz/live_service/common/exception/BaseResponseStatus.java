package back.vybz.live_service.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),
    EMAIL_CODE_SUCCESS(HttpStatus.OK, true, 201, "이메일 인증코드 발송에 성공하였습니다."),
    EMAIL_CODE_VERIFICATION_SUCCESS(HttpStatus.OK, true, 202, "이메일 인증에 성공하였습니다."),
    SIGN_UP_SUCCESS(HttpStatus.OK, true, 203, "회원가입에 성공하였습니다."),
    SIGN_IN_SUCCESS(HttpStatus.OK, true, 204, "로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, true, 205, "로그아웃 되었습니다."),
    SUCCESS_MATCH_PASSWORD(HttpStatus.OK, true, 206, "비밀번호가 확인되었습니다."),
    SUCCESS_UPDATE_PASSWORD(HttpStatus.OK, true, 207, "비밀번호가 변경되었습니다."),
    SUCCESS_UPDATE_NICKNAME(HttpStatus.OK, true, 208, "닉네임이 변경되었습니다."),
    SUCCESS_RECENT_VIEW(HttpStatus.OK, true, 209, "최근 본 상품이 등록되었습니다."),
    SUCCESS_WITHDRAWAL_USER(HttpStatus.OK, true, 210, "회원 탈퇴가 완료되었습니다. 2주안에 재 로그인 시 계정 복구가 가능합니다."),
    SUCCESS_ACCOUNT_RECOVERY(HttpStatus.OK, true, 211, "계정 복구가 완료되었습니다. 로그인 해주세요."),
    NO_OAUTH_USER(HttpStatus.OK, true, 2200, "소셜 계정이 존재하지 않습니다. 추가 정보를 입력해 회원가입 해주세요."),
    SUCCESS_DOWNLOAD_COUPON(HttpStatus.OK, true, 2201, "쿠폰 다운로드에 성공하였습니다."),
    SUCCESS_USE_COUPON(HttpStatus.OK, true, 2202, "쿠폰 사용에 성공하였습니다."),

    /**
     * 400 : security 에러
     */

    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "토큰이 유효하지 않습니다"),
    NO_SIGN_IN(HttpStatus.UNAUTHORIZED, false, 402, "로그인을 먼저 진행해주세요"),
    NO_ACCESS_AUTHORITY(HttpStatus.FORBIDDEN, false, 403, "접근 권한이 없습니다"),
    NO_AUTHORIZATION_TO_UPDATE_NOTICE(HttpStatus.FORBIDDEN,false,403, "이 공지를 수정할 권한이 없습니다."),
    NO_AUTHORIZATION_TO_DELETE_NOTICE(HttpStatus.FORBIDDEN, false, 403, "이 공지를 삭제할 권한이 없습니다."),
    DISABLED_USER(HttpStatus.FORBIDDEN, false, 404, "비활성화된 계정입니다. 계정을 복구하시겠습니까?"),
    FAILED_TO_RESTORE(HttpStatus.INTERNAL_SERVER_ERROR, false, 405, "계정 복구에 실패했습니다. 관리자에게 문의해주세요."),
    NO_EXIST_OAUTH(HttpStatus.NOT_FOUND, false, 406, "소셜 로그인 정보가 존재하지 않습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, false, 407, "이메일 또는 패스워드를 다시 확인해주세요."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, false, 408, "Refresh Token이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, false, 409, "Refresh Token이 만료되었습니다. 다시 로그인해주세요."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, false, 410, "유효하지 않은 Access Token입니다."),
    NO_EXIST_NOTICE(HttpStatus.NOT_FOUND,false,404, "존재하지 않는 공지사항입니다."),
    NO_EXIST_COMMENT_OR_NO_AUTH(HttpStatus.NOT_FOUND, false,404,"댓글이 존재하지 않거나 권한이 없습니다."),
    REELS_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 411, "릴스 저장에 실패했습니다."),
    NO_AUTHORIZATION_TO_UPDATE_REELS(HttpStatus.FORBIDDEN, false, 412, "이 릴스를 수정할 권한이 없습니다."),
    NO_AUTHORIZATION_TO_DELETE_REELS(HttpStatus.FORBIDDEN, false, 413, "이 릴스를 삭제할 권한이 없습니다."),
    FAN_FEED_NOT_FOUND(HttpStatus.NOT_FOUND, false, 414, "존재하지 않는 팬 피드입니다."),
    NO_AUTHORIZATION_TO_UPDATE_FAN_FEED(HttpStatus.FORBIDDEN, false, 415, "이 팬 피드를 수정할 권한이 없습니다."),
    NO_AUTHORIZATION_TO_DELETE_FAN_FEED(HttpStatus.FORBIDDEN, false, 416, "이 팬 피드를 삭제할 권한이 없습니다."),
    LIVE_STREAM_NOT_FOUND(HttpStatus.NOT_FOUND, false, 417, "존재하지 않는 라이브 스트림입니다."),

    /**
     * 500: 서버 에러
     */
    THUMBNAIL_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "썸네일 생성에 실패했습니다."),
    REELS_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "릴스 등록 중 오류가 발생했습니다."),
    REELS_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "릴스 수정 중 오류가 발생했습니다."),
    REELS_NOT_FOUND(HttpStatus.NOT_FOUND, false, 500, "존재하지 않는 릴스입니다."),
    NO_EXIST_FEED(HttpStatus.NOT_FOUND, false, 500, "존재하지 않는 피드입니다."),
    NOTICE_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "공지 등록에 실패했습니다."),
    FAN_FEED_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 501, "팬 피드 등록에 실패했습니다."),
    ABOUT_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 502, "자기소개 등록에 실패했습니다."),
    ABOUT_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 503, "자기소개 수정에 실패했습니다."),
    ABOUT_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 504, "자기소개 삭제에 실패했습니다."),
    ABOUT_NOT_FOUND(HttpStatus.NOT_FOUND, false, 505, "존재하지 않는 자기소개입니다."),
    NO_AUTHORIZATION_TO_UPDATE_ABOUT(HttpStatus.FORBIDDEN, false, 506, "이 자기소개를 수정할 권한이 없습니다."),
    NO_AUTHORIZATION_TO_DELETE_ABOUT(HttpStatus.FORBIDDEN, false, 507, "이 자기소개를 삭제할 권한이 없습니다."),
    ALREADY_EXISTS_ABOUT(HttpStatus.CONFLICT, false, 508, "이미 등록된 자기소개가 존재합니다."),





    /**
     * 900: 기타 에러
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "Internal server error"),
    SSE_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 901, "알림 전송에 실패하였습니다."),
    LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 902, "로그인에 실패하였습니다."),


    /**
     * Request 유효성 에러
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "잘못된 요청입니다."),


    /**
     * 2000: users service error
     */
    // token
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, false, 2001, "토큰이 유효하지 않습니다."),

    // Users
    DUPLICATED_USER(HttpStatus.CONFLICT, false, 2101, "이미 가입된 멤버입니다."),
    FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED, false, 2102, "아이디 또는 패스워드를 다시 확인하세요."),
    DUPLICATED_SOCIAL_USER(HttpStatus.CONFLICT, false, 2103, "이미 소셜 연동된 계정입니다."),
    DUPLICATED_SOCIAL_PROVIDER_USER(HttpStatus.CONFLICT, false, 2104, "계정에 동일한 플랫폼이 이미 연동되어있습니다"),
    NO_EXIST_USER(HttpStatus.NOT_FOUND, false, 2105, "존재하지 않는 멤버 정보입니다."),
    PASSWORD_SAME_FAILED(HttpStatus.BAD_REQUEST, false, 2106, "현재 사용중인 비밀번호 입니다."),
    PASSWORD_CONTAIN_NUM_FAILED(HttpStatus.BAD_REQUEST, false, 2107, "휴대폰 번호를 포함한 비밀번호 입니다."),
    PASSWORD_MATCH_FAILED(HttpStatus.BAD_REQUEST, false, 2108, "패스워드를 다시 확인해주세요."),
    NO_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, false, 2109, "지원하지 않는 플랫폼입니다"),
    SAME_NICKNAME(HttpStatus.CONFLICT, false, 2010, "현재 사용중인 닉네임입니다."),
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, false, 2011, "이메일을 다시 확인해주세요."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, false, 2012, "이미 사용 중인 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, false, 2013, "이미 사용중인 닉네임입니다."),
    DUPLICATED_PHONE_NUMBER(HttpStatus.CONFLICT, false, 2014, "이미 사용 중인 전화번호입니다."),
    PASSWORD_CONFIRM_MISMATCH(HttpStatus.BAD_REQUEST, false, 2015, "비밀번호 재입력이 일치하지 않습니다."),
    PASSWORD_CHANGE_NOT_VERIFIED(HttpStatus.BAD_REQUEST, false, 2016, "비밀번호 변경 인증이 만료되었습니다. 비밀번호를 다시 입력해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, 2017, "존재하지 않는 유저입니다."),
    SIGN_UP_NOT_VERIFIED(HttpStatus.BAD_REQUEST, false, 2018, "회원가입 인증이 만료되었습니다. 이메일을 인증을 다시 요청해주세요."),
    INVALID_PURPOSE(HttpStatus.BAD_REQUEST, false, 2019, "정의되지 않은 목적입니다."),
    WITHDRAWAL_PENDING(HttpStatus.BAD_REQUEST, false, 2020, "탈퇴 예정 계정입니다. 로그인을 원하시면 계정 복구를 진행해주세요."),
    WITHDRAWAL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, false, 2021, "탈퇴를 위한 인증이 만료되었습니다. 비밀번호를 다시 입력해주세요."),
    ACCOUNT_RECOVERY_NOT_VERIFIED(HttpStatus.BAD_REQUEST, false, 2022, "계정 복구 인증이 만료되었습니다. 이메일을 인증을 다시 요청해주세요."),

    // Agreement
    INVALID_AGREEMENT_ID(HttpStatus.BAD_REQUEST, false, 2113, "존재하지 않는 약관 ID 입니다."),
    INVALID_USER_AGREEMENT_UUID(HttpStatus.BAD_REQUEST, false, 2114, "존재하지 않는 유저-약관 UUID 입니다."),
    NO_USER_SHIPPING_ADDRESS_AGREEMENT(HttpStatus.NOT_FOUND, false, 2115, "유저의 배송지 약관 동의&비동의 여부가 존재하지 않습니다"),
    NO_USER_SIGN_UP_AGREEMENT(HttpStatus.NOT_FOUND, false, 2116, "유저의 회원가입 약관 동의&비동의 여부가 존재하지 않습니다"),

    // mail
    INVALID_EMAIL_CODE(HttpStatus.BAD_REQUEST, false, 2117, "인증코드가 틀렸습니다."),
    EXPIRED_EMAIL_CODE(HttpStatus.BAD_REQUEST, false, 2118, "틀리거나 만료된 인증코드입니다."),
    EMAIL_CODE_SEND_LIMITED(HttpStatus.TOO_MANY_REQUESTS, false, 2119,"이메일 발송은 3분에 1회 입니다. 잠시 후 다시 시도해주세요."),
    EMAIL_CODE_VERIFICATION_LIMITED(HttpStatus.TOO_MANY_REQUESTS, false, 2120, "인증에 반복적으로 실패했습니다. 다시 코드 전송을 요청해주세요."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, false, 2121, "가입되지 않은 이메일입니다."),

    // oauth

    SOCIAL_USER_PASSWORD_CHANGE(HttpStatus.BAD_REQUEST, false, 2201, "소셜로 가입한 유저는 비밀번호가 존재하지 않습니다."),
    INVALID_GOOGLE_TOKEN(HttpStatus.UNAUTHORIZED, false, 2202, "google access token이 유효하지 않습니다."),
    INVALID_NAVER_TOKEN(HttpStatus.UNAUTHORIZED, false, 2203, "naver access token이 유효하지 않습니다."),
    INVALID_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED, false, 2204, "kakao access token이 유효하지 않습니다."),
    NOT_SUPPORTED_OAUTH(HttpStatus.BAD_REQUEST, false, 2205, "지원하지 않는 소셜 로그인입니다."),
    NOT_FOUND_COOKIE(HttpStatus.BAD_REQUEST, false, 2206, "쿠키가 존재하지 않습니다."),
    INVALID_OAUTH_TOKEN(HttpStatus.BAD_REQUEST, false, 2207, "유효하지 않은 소셜 회원가입 토큰입니다."),

    /**
     * 3000: product service error
     */

    // Product
    NO_EXIST_PRODUCT(HttpStatus.NOT_FOUND, false, 3001, "존재하지 않는 상품입니다"),
    NO_EXIST_OPTION(HttpStatus.NOT_FOUND, false, 3002, "존재하지 않는 옵션입니다"),
    NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, false, 3003, "존재하지 않는 카테고리입니다"),

    DUPLICATED_PRODUCT(HttpStatus.CONFLICT, false, 3004, "이미 등록된 상품입니다"),
    DUPLICATED_OPTION(HttpStatus.CONFLICT, false, 3005, "이미 등록된 옵션입니다"),
    DUPLICATED_CATEGORY(HttpStatus.CONFLICT, false, 3006, "이미 등록된 카테고리입니다"),

    NO_EXIST_OPTIONS_IN_PRODUCT(HttpStatus.NOT_FOUND, false, 3007, "해당 상품에 옵션이 존재하지 않습니다"),

    NO_EXIST_PRODUCT_CATEGORY_LIST(HttpStatus.NOT_FOUND, false, 3008, "존재하지 않는 상품 카테고리 리스트입니다"),

    NO_EXIST_PRODUCT_OPTION(HttpStatus.NOT_FOUND, false, 3009, "존재하지 않는 상품 옵션입니다"),

    NO_EXIST_PRODUCT_DESCRIPTION(HttpStatus.NOT_FOUND, false, 3010, "존재하지 않는 상품 설명입니다"),

    NO_EXIST_PRODUCT_THUMBNAIL(HttpStatus.NOT_FOUND, false, 3011, "존재하지 않는 상품 썸네일입니다"),

    NO_EXIST_SUB_CATEGORY(HttpStatus.NOT_FOUND, false, 3012, "존재하지 않는 서브 카테고리입니다"),

    DUPLICATED_SEASON(HttpStatus.CONFLICT, false, 3013, "이미 등록된 시즌입니다"),
    NO_EXIST_SEASON(HttpStatus.NOT_FOUND, false, 3014, "존재하지 않는 시즌입니다"),
    NO_EXIST_SEASON_LIST(HttpStatus.NOT_FOUND, false, 3015, "존재하지 않는 시즌 리스트입니다"),

    NO_EXIST_EVENT(HttpStatus.NOT_FOUND, false, 3016, "존재하지 않는 기획전입니다"),

    NO_EXIST_MAIN_BANNER(HttpStatus.NOT_FOUND, false, 3017, "존재하지 않는 메인 배너입니다"),

    /**
     * 3100: review service error
     */
    NO_EXIST_REVIEW(HttpStatus.NOT_FOUND, false, 3101, "존재하지 않는 리뷰입니다"),
    NO_EXIST_REVIEW_PHOTO(HttpStatus.NOT_FOUND, false, 3102, "존재하지 않는 리뷰 사진입니다"),
    TOO_MANY_REVIEW_IMAGES(HttpStatus.BAD_REQUEST, false, 3103, "리뷰 이미지는 최대 5장까지 등록 가능합니다"),
    REVIEW_NOT_ELIGIBLE(HttpStatus.BAD_REQUEST, false, 3101, "구매하지 않은 상품은 리뷰 등록이 불가능 합니다"),
    REVIEW_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, false, 3102, "이미 리뷰가 등록된 상품입니다"),

    /**
     * 4000: comment service error
     */

    // Comment
    NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, false, 4001, "존재하지 않는 댓글입니다"),
    NO_DELETE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4002, "댓글 삭제 권한이 없습니다"),
    NO_DELETE_RE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4003, "대댓글 삭제 권한이 없습니다"),
    NO_EXIST_RE_COMMENT(HttpStatus.NOT_FOUND, false, 4003, "존재하지 않는 대댓글입니다"),
    NO_EXIST_PIN_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4004, "고정 권한이 없습니다"),

    /**
     * 5000: notification service error
     */

    // Notification
    NO_EXIST_NOTIFICATION_SETTING(HttpStatus.NOT_FOUND, false, 5001, "유저의 알림 설정이 존재하지 않습니다."),
    EXIST_NOTIFICATION_SETTING(HttpStatus.BAD_REQUEST, false, 5002, "유저의 알림 설정이 이미 존재합니다."),
    NO_EXIST_NOTIFICATION(HttpStatus.NOT_FOUND, false, 5003, "존재하지 않는 알림입니다."),
    CANNOT_SHARE(HttpStatus.BAD_REQUEST, false, 5004, "공유할 수 없는 유저입니다."),

    /**
     * 6000: gpt-api error
     */
    // Media
    NO_EXIST_MEDIA(HttpStatus.NOT_FOUND, false, 6001, "존재하지 않는 미디어입니다"),

    /**
     * 7000: cart service error
     */
    // Cart
    INVALID_CART_QUANTITY(HttpStatus.BAD_REQUEST, false, 7001, "장바구니 수량이 유효하지 않습니다"),
    DUPLICATED_CART_PRODUCT(HttpStatus.BAD_REQUEST, false, 7002, "이미 장바구니에 담긴 상품입니다"),
    NO_EXIST_CART_PRODUCT(HttpStatus.NOT_FOUND, false, 7003, "존재하지 않는 장바구니 상품입니다"),
    // payment
    PAYMENT_DUPLICATE_PAYMENT_UUID(HttpStatus.BAD_REQUEST, false, 7101, "이미 존재하는 주문번호입니다"),
    PAYMENT_NO_EXIST(HttpStatus.NOT_FOUND, false, 7102, "존재하지 않는 결제입니다"),
    PAYMENT_CONFIRM_FAIL(HttpStatus.BAD_REQUEST, false, 7103, "결제 승인에 실패했습니다"),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, false, 7104, "결제 금액이 일치하지 않습니다"),
    PAYMENT_ALREADY_DONE(HttpStatus.BAD_REQUEST, false, 7105, "이미 처리가 완료된 주문입니다"),
    TOSS_EMPTY_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, false, 7106, "TOSS 결제 승인 응답이 비어있습니다"),
    VIRTUAL_PAYMENT_FAIL(HttpStatus.BAD_REQUEST, false, 7107, "가상계좌 결제에 실패했습니다."),
    NOT_FOUND_ORDER_LIST(HttpStatus.NOT_FOUND, false, 7108, "존재하지 않는 주문 내역입니다."),
    // order
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, false, 7201, "주문 내역에 상품이 존재하지 않습니다."),
    NO_EXIST_QUERY_FOR_ORDER_DETAIL(HttpStatus.BAD_REQUEST, false, 7202, "주문 상세 조회를 위한 쿼리가 존재하지 않습니다."),
    NO_EXIST_ORDER_DETAIL(HttpStatus.NOT_FOUND, false, 7203, "존재하지 않는 주문 상품입니다."),
    // shipping adress
    NO_EXIST_USER_SHIPPING_ADDRESS(HttpStatus.NOT_FOUND, false, 7301, "해당하는 유저-배송지가 존재하지 않습니다."),
    NO_EXIST_SHIPPING_ADDRESS(HttpStatus.NOT_FOUND, false, 7302, "존재하지 않는 배송지입니다"),
    NO_EXIST_DEFAULT_SHIPPING_ADDRESS(HttpStatus.OK, true, 7303, "존재하지 않는 기본 배송지입니다"),
    /**
     * 8000: coupon service error
     */
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, false, 8001, "존재하지 않는 쿠폰입니다."),
    COUPON_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, false, 8002, "수량이 소진된 쿠폰입니다."),
    COUPON_ALREADY_DOWNLOADED(HttpStatus.BAD_REQUEST, false, 8003, "이미 다운로드한 쿠폰입니다."),
    COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST, false, 8005, "이미 사용한 쿠폰입니다.");

    private final HttpStatusCode httpStatusCode;
    private final boolean isSuccess;
    private final int code;
    private final String message;

}

