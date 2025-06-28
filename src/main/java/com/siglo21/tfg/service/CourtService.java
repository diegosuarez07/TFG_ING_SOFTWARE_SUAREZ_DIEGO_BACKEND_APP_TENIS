package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.response.CourtResponseDto;

import java.util.List;

public interface CourtService {
    List<CourtResponseDto> getAllCourts();
}
