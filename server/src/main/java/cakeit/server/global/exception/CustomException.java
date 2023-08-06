package cakeit.server.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorEnum errorEnum;
    private final String errorType;
    private final String errorEndpoint;

    public CustomException(ErrorEnum errorEnum, String errorMessage, String errorType, String errorEndpoint, Throwable cause) {
        super(errorMessage, cause);
        this.errorEnum = errorEnum;
        this.errorType = errorType;
        this.errorEndpoint = errorEndpoint;
    }
}

