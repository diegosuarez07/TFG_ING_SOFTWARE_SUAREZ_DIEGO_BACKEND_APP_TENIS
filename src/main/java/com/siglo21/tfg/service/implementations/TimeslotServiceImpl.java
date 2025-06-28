package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.request.TimeslotFilterRequestDto;
import com.siglo21.tfg.dto.response.TimeslotResponseDto;
import com.siglo21.tfg.entity.Timeslot;
import com.siglo21.tfg.enums.TimeslotStatus;
import com.siglo21.tfg.repository.TimeslotRepository;
import com.siglo21.tfg.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    @Override
    public List<TimeslotResponseDto> getAvailableTimeslots() {
        List<Timeslot> timeslots = timeslotRepository.findByStatus(TimeslotStatus.AVAILABLE.getDbValue());
        return timeslotsToDto(timeslots);
    }

    @Override
    public List<TimeslotResponseDto> getFilteredTimeslots(TimeslotFilterRequestDto filter) {
        String status = filter.getStatus() != null
                ? TimeslotStatus.fromDisplayValue(filter.getStatus()).getDbValue()
                : null;

        List<Timeslot> timeslots;

        if (filter.getCourtId() != null && filter.getDate() != null && status != null) {
            timeslots = timeslotRepository.findByCourtIdAndDateAndStatus(
                    filter.getCourtId(),
                    LocalDate.parse(filter.getDate()),
                    status
            );
        } else if (filter.getCourtId() != null && filter.getDate() != null) {
            timeslots = timeslotRepository.findByCourtIdAndDateAndStatus(
                    filter.getCourtId(),
                    LocalDate.parse(filter.getDate()),
                    TimeslotStatus.AVAILABLE.getDbValue()
            );
        } else if (filter.getCourtId() != null) {
            timeslots = timeslotRepository.findByCourtId(filter.getCourtId());
        } else if (filter.getDate() != null) {
            timeslots = timeslotRepository.findByDateAndStatus(
                    LocalDate.parse(filter.getDate()),
                    TimeslotStatus.AVAILABLE.getDbValue()
            );
        } else if (status != null) {
            timeslots = timeslotRepository.findByStatus(status);
        } else {
            timeslots = timeslotRepository.findByStatus(TimeslotStatus.AVAILABLE.getDbValue());
        }

        return timeslotsToDto(timeslots);
    }

    @Override
    public List<TimeslotResponseDto> timeslotsToDto(List<Timeslot> timeslots) {
        return timeslots.stream().map(timeslot -> {
            String statusEsp = TimeslotStatus.fromDbValue(timeslot.getStatus()).getDisplayValue();
            String courtName = timeslot.getCourt() != null ? timeslot.getCourt().getCourtName() : "";
            return new TimeslotResponseDto(
                    timeslot.getDate().toString(),
                    timeslot.getStartTime().toString().substring(0,5),
                    timeslot.getEndTime().toString().substring(0,5),
                    statusEsp,
                    courtName
            );
        }).toList();
    }
}
