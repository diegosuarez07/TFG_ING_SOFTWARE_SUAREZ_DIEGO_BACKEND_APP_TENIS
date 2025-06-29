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
public class TournamentRegistrationResponseDto {
    private Long registrationId;
    private Long tournamentId;
    private String tournamentName;
    private Long userId;
    private String userName;
    private LocalDate registrationDate;
}
