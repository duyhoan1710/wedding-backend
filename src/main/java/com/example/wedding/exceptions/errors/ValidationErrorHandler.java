package com.example.wedding.exceptions.errors;


import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.exceptions.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationErrorHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> validationErrorHandler(BindException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        String errorMessage = null;
        String debugMessage = null;

        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            debugMessage = e.getLocalizedMessage();
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        errorCode.getErrorCode(),
                        errorCode.getStatusCode(),
                        errorMessage,
                        debugMessage,
                        LocalDateTime.now()),
                errorCode.getStatusCode());
    }
}
