package com.example.wedding.services;


import com.example.wedding.dtos.auth.request.LoginDto;
import com.example.wedding.dtos.auth.request.RegisterDto;
import com.example.wedding.dtos.auth.response.LoginResponse;
import com.example.wedding.dtos.common.response.ResponseData;

public interface AuthService {
    void register(RegisterDto payload);

    ResponseData<LoginResponse> login(LoginDto payload);
}
