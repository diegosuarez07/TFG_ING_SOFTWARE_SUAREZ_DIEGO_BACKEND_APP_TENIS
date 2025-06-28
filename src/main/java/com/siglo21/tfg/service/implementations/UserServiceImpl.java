package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.request.LoginRequestDto;
import com.siglo21.tfg.dto.request.UserRequestDto;
import com.siglo21.tfg.dto.response.LoginResponseDto;
import com.siglo21.tfg.dto.response.UserInfoDto;
import com.siglo21.tfg.entity.User;
import com.siglo21.tfg.mapper.UserMapper;
import com.siglo21.tfg.repository.UserRepository;
import com.siglo21.tfg.service.JwtService;
import com.siglo21.tfg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> createUser(UserRequestDto requestDto) {
        try {
            User user = userRepository.findUserByEmail(requestDto.getEmail());
            if (user != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya se encuentra registrado");
            } else {
                user = userMapper.fromRequest(requestDto);
                user.setRegistrationDate(LocalDate.now());
                String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
                user.setPassword(hashedPassword);
                userRepository.save(user);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al almacenar usuario");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Usuario almacenado correctamente");
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        try {
            User user = userRepository.findUserByEmail(requestDto.getEmail());

            if (user == null) {
                return new LoginResponseDto(
                        false,
                        "Usuario no encontrado",
                        null
                );
            }

            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                return new LoginResponseDto(
                        false,
                        "Contraseña incorrecta",
                        null
                );
            }

            // Login exitoso - crear UserInfoDto
            UserInfoDto userInfo = new UserInfoDto(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUserType(),
                    user.getSkillLevelGame()
            );

            return new LoginResponseDto(
                    true,
                    "Login exitoso",
                    jwtService.generateToken(userInfo.getEmail(), userInfo.getId(), userInfo.getUserType()),
                    userInfo
            );

        } catch (Exception e) {
            return new LoginResponseDto(
                    false,
                    "Error interno del servidor",
                    null
            );
        }
    }
}
