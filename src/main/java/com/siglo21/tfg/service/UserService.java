package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(UserRequestDto requestDto);

}
