package com.siglo21.tfg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private boolean success;
    private String message;
    private String token;
    private UserInfoDto userInfo;

    // Constructor para login fallido
    public LoginResponseDto(boolean success, String message, UserInfoDto userInfo) {
        this.success = success;
        this.message = message;
        this.token = null;
        this.userInfo = null;
    }
}
