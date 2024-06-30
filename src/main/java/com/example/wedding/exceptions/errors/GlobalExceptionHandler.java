package com.example.wedding.exceptions.errors;


import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.exceptions.ErrorException;
import com.example.wedding.exceptions.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception e) {
        ErrorException error = (ErrorException) e;

        LocalDateTime dateTime = error.getTimestamp();
        HttpStatus statusCode = error.getStatusCode();
        int errorCode = error.getErrorCode();
        String message = error.getMessage();
        String debugMessage = error.getDebugMessage();

        return new ResponseEntity<>(
                new ErrorResponse(errorCode, statusCode, message, debugMessage, dateTime),
                statusCode);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getErrorCode(),
                        errorCode.getStatusCode(),
                        errorCode.getMessage(),
                        ex.getLocalizedMessage(),
                        LocalDateTime.now()),
                errorCode.getStatusCode());
    }
}
