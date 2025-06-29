package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.entity.Court;
import com.siglo21.tfg.entity.Timeslot;
import com.siglo21.tfg.repository.CourtRepository;
import com.siglo21.tfg.repository.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeslotGeneratorService {

    private final TimeslotRepository timeslotRepository;
    private final CourtRepository courtRepository;

    // Se ejecuta automáticamente cada día a las 12:11 pm
    @Scheduled(cron = "0 54 23 * * ?")
    public void generateTimeslotsAutomatically() {
        log.info("Iniciando generación automática de horarios...");
        generateTimeslotsForNextDays(7); // Generar para los próximos 7 días
    }

    private void generateTimeslotsForNextDays(int days) {
        try {
            List<Court> courts = courtRepository.findAll();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(days);

            log.info("Generando horarios desde {} hasta {}", startDate, endDate);
            log.info("Canchas encontradas: {}", courts.size());

            for (Court court : courts) {
                log.info("Generando horarios para cancha: {} - {}",
                        court.getCourtId(), court.getCourtName());
                generateTimeslotsForCourt(court, startDate, endDate);
            }

            log.info("Horarios generados exitosamente para {} días", days);
        } catch (Exception e) {
            log.error("Error generando horarios: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateTimeslotsForCourt(Court court, LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        int horariosGenerados = 0;

        while (!currentDate.isAfter(endDate)) {
            // Generar para TODOS los días (incluyendo sábados y domingos)
            generateTimeslotsForDate(court, currentDate);
            currentDate = currentDate.plusDays(1);
        }

        log.info("Generados {} horarios para cancha {}", horariosGenerados, court.getCourtName());
    }

    private void generateTimeslotsForDate(Court court, LocalDate date) {
        // Verificar si ya existen horarios para esta fecha y cancha
        if (timeslotRepository.existsByCourtIdAndDate(court.getCourtId(), date)) {
            log.debug("Ya existen horarios para cancha {} en fecha {}",
                    court.getCourtName(), date);
            return; // Ya existen horarios para esta fecha
        }

        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endTime = LocalTime.of(21, 0);   // 9:00 PM

        LocalTime currentTime = startTime;
        int horariosParaEstaFecha = 0;

        while (currentTime.isBefore(endTime)) {
            Timeslot timeslot = new Timeslot();
            timeslot.setCourtId(court.getCourtId());
            timeslot.setDate(date);
            timeslot.setStartTime(currentTime);
            timeslot.setEndTime(currentTime.plusHours(1));
            timeslot.setStatus("AVAILABLE");

            timeslotRepository.save(timeslot);
            horariosParaEstaFecha++;

            currentTime = currentTime.plusHours(1);
        }

        log.info("Generados {} horarios para cancha {} en fecha {}",
                horariosParaEstaFecha, court.getCourtName(), date);
    }
}
