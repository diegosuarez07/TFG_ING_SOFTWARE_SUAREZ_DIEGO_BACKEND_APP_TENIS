package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.request.TournamentRequestDto;
import com.siglo21.tfg.dto.response.TournamentResponseDto;
import com.siglo21.tfg.entity.Tournament;
import com.siglo21.tfg.service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/tournaments")
@CrossOrigin(origins = "*")
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<?> createTournament(@RequestBody @Valid TournamentRequestDto dto) {
        try {
            Tournament tournament = tournamentService.createTournament(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(tournament);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<TournamentResponseDto>> getAvailableTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAvailableTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping
    public ResponseEntity<List<TournamentResponseDto>> getAllTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }
}
