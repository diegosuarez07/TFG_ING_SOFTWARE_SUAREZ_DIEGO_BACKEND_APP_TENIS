package com.siglo21.tfg.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CourtBookingResponseDto {

    private Long bookingId;
    private Long userId;
    private Long timeslotId;
    private String bookingDate;
    private String status;

    // Información adicional del timeslot
    private String date;
    private String startTime;
    private String endTime;
    private String courtName;
    private String courtNumber;


}
