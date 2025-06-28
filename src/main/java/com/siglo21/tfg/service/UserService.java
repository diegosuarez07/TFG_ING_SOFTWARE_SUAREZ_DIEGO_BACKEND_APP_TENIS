package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.LoginRequestDto;
import com.siglo21.tfg.dto.request.UserRequestDto;
import com.siglo21.tfg.dto.response.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(UserRequestDto requestDto);
    LoginResponseDto login(LoginRequestDto requestDto);
}
