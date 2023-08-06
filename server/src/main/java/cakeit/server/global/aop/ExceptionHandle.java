package cakeit.server.global.aop;

import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.CustomException;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> customExceptionHandler(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(e.getErrorEnum().getCode())
                .errorMessage(e.getMessage())
                .errorEndPoint(e.getErrorEndpoint())
                .errorType(e.getErrorType())
                .build();
        log.error("[ERROR]", e.getCause());
        return ResponseEntity.status(e.getErrorEnum().getHttpStatus())
                .body(CommonResponse.fail(errorResponse));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorEnum.UNPROCESSABLE_ENTITY.getCode())
                .errorMessage(ErrorEnum.UNPROCESSABLE_ENTITY.getMessage())
                .errorEndPoint(null)
                .errorType(null)
                .build();

        log.error("[ERROR]", e);
        return ResponseEntity.status(ErrorEnum.UNPROCESSABLE_ENTITY.getHttpStatus())
                .body(CommonResponse.fail(errorResponse));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> generalExceptionHandler(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorMessage(ErrorEnum.INTERNAL_SERVER_ERROR.getMessage())
                .errorEndPoint(null)
                .errorType(null)
                .build();

        log.error("[ERROR]", e);
        return ResponseEntity.status(ErrorEnum.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(CommonResponse.fail(errorResponse));
    }
}
