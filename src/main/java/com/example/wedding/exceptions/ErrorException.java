package com.example.wedding.exceptions;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorException extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private int errorCode;
    private HttpStatus statusCode;
    private String message;
    private String debugMessage;

    private ErrorException() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorException(ErrorCode errorCode) {
        this();
        this.errorCode = errorCode.getErrorCode();
        this.statusCode = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }

    public ErrorException(Throwable ex) {
        this();
        this.statusCode = ErrorCode.INTERNAL_SERVER_ERROR.getStatusCode();
        this.message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        this.debugMessage = ex.getLocalizedMessage();
    }
}
