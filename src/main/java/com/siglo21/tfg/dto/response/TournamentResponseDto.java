package com.siglo21.tfg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TournamentResponseDto {
    private Long tournamentId;
    private String name;
    private String category;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;
    private LocalDate tournamentStartDate;
    private LocalDate tournamentEndDate;
    private Integer maxPlayers;
    private Long createdBy;
}
