package com.siglo21.tfg.dto.request;

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
public class TimeslotFilterRequestDto {

    private Long courtId;
    private String date;
    private String status;

}
