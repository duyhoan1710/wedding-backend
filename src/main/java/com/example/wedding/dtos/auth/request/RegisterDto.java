package com.example.wedding.dtos.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class RegisterDto {
    @NotEmpty @Email String email;

    @NotBlank String name;

    @NotBlank String password;
}
