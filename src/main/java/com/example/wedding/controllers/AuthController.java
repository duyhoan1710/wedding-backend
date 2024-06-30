package com.example.wedding.controllers;


import com.example.wedding.dtos.auth.request.LoginDto;
import com.example.wedding.dtos.auth.request.RegisterDto;
import com.example.wedding.dtos.auth.response.LoginResponse;
import com.example.wedding.dtos.common.response.ResponseData;
import com.example.wedding.services.AuthService;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Value
@RestController()
@RequestMapping("auth")
public class AuthController {
    AuthService authService;

    @PostMapping("/register")
    void register(@RequestBody @Valid RegisterDto payload) {
        authService.register(payload);
    }

    @PostMapping("/login")
    ResponseData<LoginResponse> login(@RequestBody @Valid LoginDto payload) {
        return authService.login(payload);
    }
}
