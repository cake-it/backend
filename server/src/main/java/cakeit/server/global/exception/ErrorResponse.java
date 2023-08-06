package cakeit.server.global.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    // error code
    private String errorCode;
    // error message
    private String errorMessage;
    // error category
    private String errorType;
    // error url end point
    private String errorEndPoint;


}