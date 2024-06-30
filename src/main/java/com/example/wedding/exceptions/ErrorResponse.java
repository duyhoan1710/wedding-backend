package com.example.wedding.exceptions;


import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int errorCode;
    private HttpStatus statusCode;
    private String message;
    private String debugMessage;

    public ErrorResponse(
            int errorCode,
            HttpStatus statusCode,
            String message,
            String debugMessage,
            LocalDateTime timestamp) {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
