package com.example.wedding.services.serviceImpl;


import com.example.wedding.dtos.auth.request.LoginDto;
import com.example.wedding.dtos.auth.request.RegisterDto;
import com.example.wedding.dtos.auth.response.LoginResponse;
import com.example.wedding.dtos.common.response.ResponseData;
import com.example.wedding.entities.User;
import com.example.wedding.enums.RoleEnum;
import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.exceptions.ErrorException;
import com.example.wedding.repositories.UserRepository;
import com.example.wedding.services.AuthService;
import com.example.wedding.utils.jwt.JwtUtil;
import java.security.PrivateKey;
import java.util.Optional;

import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Value
@Service
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    PrivateKey privateKey;

    public void register(RegisterDto payload) {
        Optional<User> user = userRepository.findByEmail(payload.getEmail());

        if (user.isPresent()) {
            throw new ErrorException(ErrorCode.DUPLICATE_USER);
        }

        User newUser = new User();
        newUser.setEmail(payload.getEmail());
        newUser.setName(payload.getName());
        newUser.setPassword(passwordEncoder.encode(payload.getPassword()));
        newUser.setRole(RoleEnum.USER);

        userRepository.save(newUser);
    }

    public ResponseData<LoginResponse> login(LoginDto payload) {
        User user = userRepository.findByEmail(payload.getEmail()).orElse(null);

        if (user == null) {
            throw new ErrorException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            throw new ErrorException(ErrorCode.PASSWORD_INCORRECT);
        }

        String accessToken = JwtUtil.generateAccessToken(user, privateKey);

        return new ResponseData<>(new LoginResponse(accessToken));
    }
}
