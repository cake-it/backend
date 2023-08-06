package cakeit.server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {
    /**
     * System Exception
     */

    CONTINUE(HttpStatus.CONTINUE, "E100", "Continue"),
    SWITCHING_PROTOCOLS(HttpStatus.SWITCHING_PROTOCOLS, "E101", "Switching Protocols"),
    PROCESSING(HttpStatus.PROCESSING, "E102", "Processing"),

    // 2xx Success
    OK(HttpStatus.OK, "E200", "OK"),
    CREATED(HttpStatus.CREATED, "E201", "Created"),
    ACCEPTED(HttpStatus.ACCEPTED, "E202", "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "E203", "Non-Authoritative Information"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "E204", "No Content"),
    RESET_CONTENT(HttpStatus.RESET_CONTENT, "E205", "Reset Content"),
    PARTIAL_CONTENT(HttpStatus.PARTIAL_CONTENT, "E206", "Partial Content"),
    MULTI_STATUS(HttpStatus.MULTI_STATUS, "E207", "Multi-Status"),
    ALREADY_REPORTED(HttpStatus.ALREADY_REPORTED, "E208", "Already Reported"),
    IM_USED(HttpStatus.IM_USED, "E226", "IM Used"),

    // 3xx Redirection
    MULTIPLE_CHOICES(HttpStatus.MULTIPLE_CHOICES, "E300", "Multiple Choices"),
    MOVED_PERMANENTLY(HttpStatus.MOVED_PERMANENTLY, "E301", "Moved Permanently"),
    FOUND(HttpStatus.FOUND, "E302", "Found"),
    SEE_OTHER(HttpStatus.SEE_OTHER, "E303", "See Other"),
    NOT_MODIFIED(HttpStatus.NOT_MODIFIED, "E304", "Not Modified"),
    USE_PROXY(HttpStatus.USE_PROXY, "E305", "Use Proxy"),
    TEMPORARY_REDIRECT(HttpStatus.TEMPORARY_REDIRECT, "E307", "Temporary Redirect"),
    PERMANENT_REDIRECT(HttpStatus.PERMANENT_REDIRECT, "E308", "Permanent Redirect"),

    // 4xx Client Errors
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "E400", "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E401", "Unauthorized"),
    PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, "E402", "Payment Required"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E403", "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E404", "Not Found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E405", "Method Not Allowed"),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "E406", "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, "E407", "Proxy Authentication Required"),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "E408", "Request Timeout"),
    CONFLICT(HttpStatus.CONFLICT, "E409", "Conflict"),
    GONE(HttpStatus.GONE, "E410", "Gone"),
    LENGTH_REQUIRED(HttpStatus.LENGTH_REQUIRED, "E411", "Length Required"),
    PRECONDITION_FAILED(HttpStatus.PRECONDITION_FAILED, "E412", "Precondition Failed"),
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "E413", "Payload Too Large"),
    URI_TOO_LONG(HttpStatus.URI_TOO_LONG, "E414", "URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "E415", "Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "E416", "Range Not Satisfiable"),
    EXPECTATION_FAILED(HttpStatus.EXPECTATION_FAILED, "E417", "Expectation Failed"),
    IM_A_TEAPOT(HttpStatus.I_AM_A_TEAPOT, "E418", "I'm a teapot"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "E422", "Validation failed"),

    // 5xx Server Errors
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E500", "Internal Server Error"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "E501", "Not Implemented"),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "E502", "Bad Gateway"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "E503", "Service Unavailable"),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "E504", "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "E505", "HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES(HttpStatus.VARIANT_ALSO_NEGOTIATES, "E506", "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "E507", "Insufficient Storage"),
    LOOP_DETECTED(HttpStatus.LOOP_DETECTED, "E508", "Loop Detected"),
    NOT_EXTENDED(HttpStatus.NOT_EXTENDED, "E510", "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "E511", "Network Authentication Required"),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E009", "Database Exception 발생"),

    // Custom Errors

    ALREADY_BOOKED(HttpStatus.SERVICE_UNAVAILABLE, "RC001", "Already Reserved Time");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorEnum(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.message = message;
    }
}
