package com.example.wedding.exceptions.errors;


import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.exceptions.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AuthenticationErrorHandler {
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> authenticationErrorHandler(Exception ex) {
        ErrorCode errorCode = ErrorCode.AUTHENTICATION_ERROR;

        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getErrorCode(),
                        errorCode.getStatusCode(),
                        ex.getLocalizedMessage(),
                        null,
                        LocalDateTime.now()),
                errorCode.getStatusCode());
    }
}
