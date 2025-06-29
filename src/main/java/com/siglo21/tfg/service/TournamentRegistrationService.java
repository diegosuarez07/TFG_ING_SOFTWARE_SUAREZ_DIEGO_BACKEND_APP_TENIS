package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.TournamentRegistrationRequestDto;
import com.siglo21.tfg.dto.response.TournamentRegistrationResponseDto;

import java.util.List;

public interface TournamentRegistrationService {

    TournamentRegistrationResponseDto createRegistration(TournamentRegistrationRequestDto createDto);
    List<TournamentRegistrationResponseDto> getUserRegistrations(Long userId);
    List<TournamentRegistrationResponseDto> getTournamentRegistrations(Long tournamentId);
    TournamentRegistrationResponseDto getRegistrationById(Long registrationId);
    TournamentRegistrationResponseDto cancelRegistration(Long registrationId, Long userId);
    boolean isUserRegistered(Long tournamentId, Long userId);

}
