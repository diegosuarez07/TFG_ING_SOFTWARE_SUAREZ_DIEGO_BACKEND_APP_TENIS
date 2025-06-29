package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.request.TournamentRequestDto;
import com.siglo21.tfg.dto.response.TournamentResponseDto;
import com.siglo21.tfg.entity.Tournament;
import com.siglo21.tfg.repository.TournamentRepository;
import com.siglo21.tfg.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Override
    public Tournament createTournament(TournamentRequestDto requestDto) {
        if (requestDto.getName() == null || requestDto.getName().isBlank() ||
                requestDto.getCategory() == null || requestDto.getCategory().isBlank() ||
                requestDto.getRegistrationStartDate() == null ||
                requestDto.getRegistrationEndDate() == null ||
                requestDto.getTournamentStartDate() == null ||
                requestDto.getTournamentEndDate() == null ||
                requestDto.getMaxPlayers() == null ||
                requestDto.getCreatedBy() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }
        if (requestDto.getMaxPlayers() <= 0) {
            throw new IllegalArgumentException("El cupo de jugadores debe ser mayor a 0.");
        }
        LocalDate now = LocalDate.now();
        if (requestDto.getRegistrationStartDate().isBefore(now)) {
            throw new IllegalArgumentException("El inicio del periodo de inscripciones no puede ser antes de la fecha actual.");
        }
        if (requestDto.getRegistrationEndDate().isBefore(requestDto.getRegistrationStartDate())) {
            throw new IllegalArgumentException("El fin del periodo de inscripciones no puede ser anterior al inicio.");
        }
        if (requestDto.getTournamentStartDate().isBefore(now)) {
            throw new IllegalArgumentException("La fecha de inicio de torneo debe ser posterior a la fecha actual.");
        }
        if (requestDto.getTournamentStartDate().isBefore(requestDto.getRegistrationStartDate())) {
            throw new IllegalArgumentException("La fecha de inicio de torneo debe ser posterior a la fecha de inicio de inscripciones.");
        }
        if (requestDto.getTournamentStartDate().isBefore(requestDto.getRegistrationEndDate())) {
            throw new IllegalArgumentException("La fecha de inicio de torneo debe ser posterior a la fecha de fin de inscripciones.");
        }

        Tournament tournament = new Tournament(
                null,
                requestDto.getName(),
                requestDto.getCategory(),
                requestDto.getRegistrationStartDate(),
                requestDto.getRegistrationEndDate(),
                requestDto.getTournamentStartDate(),
                requestDto.getTournamentEndDate(),
                requestDto.getMaxPlayers(),
                requestDto.getCreatedBy()
        );
        return tournamentRepository.save(tournament);
    }

    @Override
    public List<TournamentResponseDto> getAvailableTournaments() {
        LocalDate now = LocalDate.now();
        List<Tournament> tournaments = tournamentRepository
                .findByRegistrationEndDateAfterAndTournamentEndDateAfterOrderByTournamentStartDateAsc(now, now);

        return tournaments.stream()
                .map(t -> new TournamentResponseDto(
                        t.getTournamentId(),
                        t.getName(),
                        t.getCategory(),
                        t.getRegistrationStartDate(),
                        t.getRegistrationEndDate(),
                        t.getTournamentStartDate(),
                        t.getTournamentEndDate(),
                        t.getMaxPlayers(),
                        t.getCreatedBy()
                ))
                .collect(Collectors.toList());
    }
}
