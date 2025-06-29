package com.siglo21.tfg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TournamentRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private LocalDate registrationStartDate;

    @NotNull
    private LocalDate registrationEndDate;

    @NotNull
    private LocalDate tournamentStartDate;

    @NotNull
    private LocalDate tournamentEndDate;

    @NotNull
    private Integer maxPlayers;

    @NotNull
    private Long createdBy;


}
