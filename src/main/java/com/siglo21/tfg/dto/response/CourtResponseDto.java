package com.siglo21.tfg.dto.response;

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
public class CourtResponseDto {

    private Long courtId;
    private Integer courtNumber;
    private String courtName;
    private String surfaceType;
    private String status;

}
