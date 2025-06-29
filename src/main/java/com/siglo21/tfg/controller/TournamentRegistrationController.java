package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.request.TournamentRegistrationRequestDto;
import com.siglo21.tfg.dto.response.TournamentRegistrationResponseDto;
import com.siglo21.tfg.service.TournamentRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tournament-registrations")
@RequiredArgsConstructor
public class TournamentRegistrationController {

    private final TournamentRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<TournamentRegistrationResponseDto> createRegistration(@Valid @RequestBody TournamentRegistrationRequestDto requestDto) {
        log.info("Recibida solicitud para crear inscripción: {}", requestDto);

        TournamentRegistrationResponseDto response = registrationService.createRegistration(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TournamentRegistrationResponseDto>> getUserRegistrations(@PathVariable Long userId) {
        log.info("Recibida solicitud para obtener inscripciones del usuario: {}", userId);

        List<TournamentRegistrationResponseDto> registrations = registrationService.getUserRegistrations(userId);

        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<TournamentRegistrationResponseDto>> getTournamentRegistrations(@PathVariable Long tournamentId) {
        log.info("Recibida solicitud para obtener inscripciones del torneo: {}", tournamentId);

        List<TournamentRegistrationResponseDto> registrations = registrationService.getTournamentRegistrations(tournamentId);

        return ResponseEntity.ok(registrations);
    }


    @GetMapping("/{registrationId}")
    public ResponseEntity<TournamentRegistrationResponseDto> getRegistrationById(@PathVariable Long registrationId) {
        log.info("Recibida solicitud para obtener inscripción con ID: {}", registrationId);

        TournamentRegistrationResponseDto registration = registrationService.getRegistrationById(registrationId);

        return ResponseEntity.ok(registration);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<TournamentRegistrationResponseDto> cancelRegistration(
            @PathVariable Long registrationId,
            @RequestParam Long userId) {
        log.info("Recibida solicitud para cancelar inscripción: {} por usuario: {}", registrationId, userId);

        TournamentRegistrationResponseDto registration = registrationService.cancelRegistration(registrationId, userId);

        return ResponseEntity.ok(registration);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserRegistered(
            @RequestParam Long tournamentId,
            @RequestParam Long userId) {
        log.info("Recibida solicitud para verificar si usuario {} está inscrito en torneo {}", userId, tournamentId);

        boolean isRegistered = registrationService.isUserRegistered(tournamentId, userId);

        return ResponseEntity.ok(isRegistered);
    }

}
