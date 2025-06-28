package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.response.CourtResponseDto;
import com.siglo21.tfg.entity.Court;
import com.siglo21.tfg.repository.CourtRepository;
import com.siglo21.tfg.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    @Override
    public List<CourtResponseDto> getAllCourts() {
        List<Court> courts = courtRepository.findAll();
        return courts.stream()
                .map(court -> new CourtResponseDto(
                        court.getCourtId(),
                        court.getCourtNumber(),
                        court.getCourtName(),
                        court.getSurfaceType(),
                        court.getStatus()
                ))
                .collect(Collectors.toList());
    }
}