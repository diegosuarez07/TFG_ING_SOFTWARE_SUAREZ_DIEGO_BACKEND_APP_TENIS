package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.request.TimeslotFilterRequestDto;
import com.siglo21.tfg.dto.response.TimeslotResponseDto;
import com.siglo21.tfg.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/timeslots")
@CrossOrigin(origins = "*")
public class TimeslotController {

    private final TimeslotService timeslotService;

    @GetMapping("/available")
    public List<TimeslotResponseDto> getAllAvailableTimeslots() {
        return timeslotService.getAvailableTimeslots();
    }

    @PostMapping("/available/filter")
    public List<TimeslotResponseDto> getFilteredTimeslots(@RequestBody TimeslotFilterRequestDto filter) {
        return timeslotService.getFilteredTimeslots(filter);
    }

}
