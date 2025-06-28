package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.response.CourtResponseDto;
import com.siglo21.tfg.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public List<CourtResponseDto> getAllCourts() {
        return courtService.getAllCourts();
    }
}