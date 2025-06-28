package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.TimeslotFilterRequestDto;
import com.siglo21.tfg.dto.response.TimeslotResponseDto;
import com.siglo21.tfg.entity.Timeslot;

import java.util.List;

public interface TimeslotService {

    List<TimeslotResponseDto> getAvailableTimeslots();
    List<TimeslotResponseDto> getFilteredTimeslots(TimeslotFilterRequestDto filter);
    List<TimeslotResponseDto> timeslotsToDto(List<Timeslot> timeslots);

}
