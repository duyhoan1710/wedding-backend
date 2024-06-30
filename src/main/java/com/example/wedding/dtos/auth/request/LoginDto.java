package com.example.wedding.dtos.auth.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotEmpty @Email String email;

    @NotBlank @NotEmpty String password;
}
