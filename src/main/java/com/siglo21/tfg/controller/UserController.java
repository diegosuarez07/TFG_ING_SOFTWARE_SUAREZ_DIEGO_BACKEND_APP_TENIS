package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.request.UserRequestDto;
import com.siglo21.tfg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    ResponseEntity<?> createUser(@RequestBody UserRequestDto requestDto){
        return userService.createUser(requestDto);
    }


}
