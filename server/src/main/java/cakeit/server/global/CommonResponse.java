package cakeit.server.global;

import cakeit.server.global.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private Status status;
    private String message;
    private T data;
    private ErrorResponse error;

    public static <T> CommonResponse<T> success(String message, T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .status(Status.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(null, data);
    }

    public static <T> CommonResponse<T> success(String message) {
        return success(message, null);
    }

    public static CommonResponse fail(String message, ErrorResponse error) {
        return CommonResponse.builder()
                .status(Status.FAIL)
                .message(message)
                .error(error)
                .build();
    }

    public static CommonResponse fail(ErrorResponse error) {
        return CommonResponse.builder()
                .status(Status.FAIL)
                .error(error)
                .build();
    }

    public static <T> CommonResponse fail(ErrorResponse error, T data) {
        return CommonResponse.builder()
                .status(Status.FAIL)
                .error(error)
                .data(data)
                .build();
    }


    public enum Status {
        SUCCESS, FAIL
    }
}