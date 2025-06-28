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
public class TimeslotResponseDto {

    private String date;
    private String startTime;
    private String endTime;
    private String status;
    private String courtName;

}
