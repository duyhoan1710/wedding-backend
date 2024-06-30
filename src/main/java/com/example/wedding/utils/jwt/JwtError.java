package com.example.wedding.utils.jwt;


import com.example.wedding.exceptions.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtError extends AuthenticationException {
    public JwtError(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
