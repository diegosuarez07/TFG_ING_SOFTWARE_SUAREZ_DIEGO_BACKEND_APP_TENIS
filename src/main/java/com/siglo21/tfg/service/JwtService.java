package com.siglo21.tfg.service;

public interface JwtService {

    String generateToken(String email, Long userId, String userType);
    boolean validateToken(String token);
    String extractEmail(String token);
    Long extractUserId(String token);
    String extractUserType(String token);

}
