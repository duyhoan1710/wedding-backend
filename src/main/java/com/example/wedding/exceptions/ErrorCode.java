package com.example.wedding.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    VALIDATION_ERROR(-1, HttpStatus.BAD_REQUEST, "Validate data error"),

    INTERNAL_SERVER_ERROR(0, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),

    DUPLICATE_USER(1, HttpStatus.BAD_REQUEST, "This user already exists."),
    USER_NOT_FOUND(2, HttpStatus.NOT_FOUND, "User not found"),

    PASSWORD_INCORRECT(3, HttpStatus.BAD_REQUEST, "Password incorrect"),
    AUTHENTICATION_NOT_FOUND(4, HttpStatus.UNAUTHORIZED, "Authentication token not found"),
    AUTHENTICATION_ERROR(5, HttpStatus.UNAUTHORIZED, "Authentication error"),

    TEMPLATE_NOT_FOUND(6, HttpStatus.NOT_FOUND, "Template not found"),
    TEMPLATE_ALREADY_EXISTS(7, HttpStatus.CONFLICT, "Template already exists");

    private final int errorCode;
    private final HttpStatus statusCode;
    private final String message;
}
