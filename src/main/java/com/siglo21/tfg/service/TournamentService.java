package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.TournamentRequestDto;
import com.siglo21.tfg.dto.response.TournamentResponseDto;
import com.siglo21.tfg.entity.Tournament;

import java.util.List;

public interface TournamentService {

    Tournament createTournament(TournamentRequestDto requestDto);
    List<TournamentResponseDto> getAvailableTournaments();

}
