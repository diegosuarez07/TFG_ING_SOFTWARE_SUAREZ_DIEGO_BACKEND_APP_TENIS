package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.request.TournamentRegistrationRequestDto;
import com.siglo21.tfg.dto.response.TournamentRegistrationResponseDto;
import com.siglo21.tfg.entity.Tournament;
import com.siglo21.tfg.entity.TournamentRegistration;
import com.siglo21.tfg.entity.User;
import com.siglo21.tfg.exception.ResourceNotFoundException;
import com.siglo21.tfg.repository.TournamentRegistrationRepository;
import com.siglo21.tfg.repository.TournamentRepository;
import com.siglo21.tfg.repository.UserRepository;
import com.siglo21.tfg.service.TournamentRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TournamentRegistrationServiceImpl implements TournamentRegistrationService {

    private final TournamentRegistrationRepository registrationRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;


    @Override
    public TournamentRegistrationResponseDto createRegistration(TournamentRegistrationRequestDto requestDto) {

        Tournament tournament = tournamentRepository.findById(requestDto.getTournamentId())
                .orElseThrow(() -> new ResourceNotFoundException("Torneo no encontrado con ID: " + requestDto.getTournamentId()));


        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + requestDto.getUserId()));

        LocalDate today = LocalDate.now();
        if (today.isBefore(tournament.getRegistrationStartDate()) || today.isAfter(tournament.getRegistrationEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El período de registro para este torneo no está abierto");
        }

        if (isUserRegistered(requestDto.getTournamentId(), requestDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya está inscrito en este torneo");

        }

        long currentRegistrations = registrationRepository.countByTournamentId(requestDto.getTournamentId());
        if (currentRegistrations >= tournament.getMaxPlayers()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El torneo ya no tiene cupos disponibles");

        }

        TournamentRegistration registration = new TournamentRegistration();
        registration.setTournamentId(requestDto.getTournamentId());
        registration.setUserId(requestDto.getUserId());
        registration.setTournament(tournament);
        registration.setUser(user);

        TournamentRegistration savedRegistration = registrationRepository.save(registration);
        tournamentRepository.save(tournament);

        log.info("Inscripción creada exitosamente con ID: {}", savedRegistration.getRegistrationId());

        return mapToResponseDto(savedRegistration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentRegistrationResponseDto> getUserRegistrations(Long userId) {
        log.info("Obteniendo inscripciones del usuario: {}", userId);

        List<TournamentRegistration> registrations = registrationRepository.findByUserIdWithDetails(userId);

        return registrations.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentRegistrationResponseDto> getTournamentRegistrations(Long tournamentId) {
        log.info("Obteniendo inscripciones del torneo: {}", tournamentId);

        List<TournamentRegistration> registrations = registrationRepository.findByTournamentIdWithDetails(tournamentId);

        return registrations.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TournamentRegistrationResponseDto getRegistrationById(Long registrationId) {
        log.info("Obteniendo inscripción con ID: {}", registrationId);

        TournamentRegistration registration = registrationRepository.findByIdWithDetails(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + registrationId));

        return mapToResponseDto(registration);
    }

    @Override
    @Transactional
    public TournamentRegistrationResponseDto cancelRegistration(Long registrationId, Long userId) {
        log.info("Cancelando inscripción: {} por usuario: {}", registrationId, userId);

        TournamentRegistration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + registrationId));

        if (!registration.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienes permisos para cancelar esta inscripción");

        }

        registrationRepository.delete(registration);

        Tournament tournament = tournamentRepository.findById(registration.getTournamentId())
                .orElseThrow(() -> new ResourceNotFoundException("Torneo no encontrado"));

        tournamentRepository.save(tournament);

        log.info("Inscripción cancelada exitosamente");

        return mapToResponseDto(registration, tournament, null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserRegistered(Long tournamentId, Long userId) {
        return registrationRepository.existsByTournamentIdAndUserId(tournamentId, userId);
    }

    private TournamentRegistrationResponseDto mapToResponseDto(TournamentRegistration registration) {
        Tournament tournament = registration.getTournament();
        User user = registration.getUser();

        return new TournamentRegistrationResponseDto(
                registration.getRegistrationId(),
                registration.getTournamentId(),
                tournament != null ? tournament.getName() : null,
                registration.getUserId(),
                user != null ? user.getFirstName() + " " + user.getLastName() : null,
                registration.getRegistrationDate()
        );
    }

    private TournamentRegistrationResponseDto mapToResponseDto(TournamentRegistration registration, Tournament tournament, User user) {
        return new TournamentRegistrationResponseDto(
                registration.getRegistrationId(),
                registration.getTournamentId(),
                tournament != null ? tournament.getName() : null,
                registration.getUserId(),
                user != null ? user.getFirstName() + " " + user.getLastName() : null,
                registration.getRegistrationDate()
        );
    }
}
