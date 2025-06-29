package com.siglo21.tfg.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TournamentRegistrationRequestDto {

    @NotNull(message = "El ID del torneo es obligatorio")
    @Min(value = 1, message = "El ID del torneo debe ser mayor a 0")
    private Long tournamentId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Min(value = 1, message = "El ID del usuario debe ser mayor a 0")
    private Long userId;


}
