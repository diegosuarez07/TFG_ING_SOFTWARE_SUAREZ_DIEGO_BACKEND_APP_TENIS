package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.entity.Court;
import com.siglo21.tfg.entity.Timeslot;
import com.siglo21.tfg.repository.CourtRepository;
import com.siglo21.tfg.repository.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeslotGeneratorService {

    private final TimeslotRepository timeslotRepository;
    private final CourtRepository courtRepository;

    // Se ejecuta automáticamente cada día a las 12:00 am
    @Scheduled(cron = "0 21 13 * * ?")
    @Transactional
    public void generateTimeslotsAutomatically() {
        log.info("Iniciando generación automática de horarios...");
        generateTimeslotsForNextDays(7); // Generar para los próximos 7 días
    }

    @Transactional
    public void generateTimeslotsForNextDays(int days) {
        try {
            // 1. ELIMINAR HORARIOS ANTIGUOS (anteriores a hoy)
            deleteOldTimeslots();

            // 2. OBTENER CANCHAS
            List<Court> courts = courtRepository.findAll();
            if (courts.isEmpty()) {
                log.warn("No se encontraron canchas para generar horarios");
                return;
            }

            // 3. GENERAR HORARIOS DESDE HOY
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(days - 1); // -1 porque ya incluimos hoy

            log.info("Generando horarios desde {} hasta {} ({} días)", startDate, endDate, days);
            log.info("Canchas encontradas: {}", courts.size());

            int totalTimeslotsGenerated = 0;
            for (Court court : courts) {
                log.info("Generando horarios para cancha: {} - {}",
                        court.getCourtId(), court.getCourtName());
                int timeslotsForCourt = generateTimeslotsForCourt(court, startDate, endDate);
                totalTimeslotsGenerated += timeslotsForCourt;
            }

            log.info("Generación completada: {} horarios generados para {} canchas en {} días",
                    totalTimeslotsGenerated, courts.size(), days);

        } catch (Exception e) {
            log.error("Error generando horarios: {}", e.getMessage(), e);
            throw new RuntimeException("Error en la generación de horarios", e);
        }
    }

    @Transactional
    public void deleteOldTimeslots() {
        LocalDate today = LocalDate.now();
        int deletedCount = timeslotRepository.deleteByDateBefore(today);
        log.info("Eliminados {} horarios anteriores a {}", deletedCount, today);
    }

    private int generateTimeslotsForCourt(Court court, LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        int totalTimeslotsGenerated = 0;

        while (!currentDate.isAfter(endDate)) {
            int timeslotsForDate = generateTimeslotsForDate(court, currentDate);
            totalTimeslotsGenerated += timeslotsForDate;
            currentDate = currentDate.plusDays(1);
        }

        log.info("Generados {} horarios para cancha {}", totalTimeslotsGenerated, court.getCourtName());
        return totalTimeslotsGenerated;
    }

    private int generateTimeslotsForDate(Court court, LocalDate date) {
        // Verificar si ya existen horarios para esta fecha y cancha
        if (timeslotRepository.existsByCourtIdAndDate(court.getCourtId(), date)) {
            log.debug("Ya existen horarios para cancha {} en fecha {}",
                    court.getCourtName(), date);
            return 0; // Ya existen horarios para esta fecha
        }

        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endTime = LocalTime.of(21, 0);   // 9:00 PM
        LocalTime currentTime = startTime;
        int timeslotsGenerated = 0;

        while (currentTime.isBefore(endTime)) {
            Timeslot timeslot = new Timeslot();
            timeslot.setCourtId(court.getCourtId());
            timeslot.setDate(date);
            timeslot.setStartTime(currentTime);
            timeslot.setEndTime(currentTime.plusHours(1));
            timeslot.setStatus("AVAILABLE");

            timeslotRepository.save(timeslot);
            timeslotsGenerated++;
            currentTime = currentTime.plusHours(1);
        }

        log.debug("Generados {} horarios para cancha {} en fecha {}",
                timeslotsGenerated, court.getCourtName(), date);
        return timeslotsGenerated;
    }


    @Transactional
    public void regenerateAllTimeslots(int days) {
        log.info("Iniciando regeneración manual de horarios para {} días", days);
        deleteOldTimeslots();
        generateTimeslotsForNextDays(days);
        log.info("Regeneración manual completada");
    }

    @Transactional
    public void generateTimeslotsForSpecificCourt(Long courtId, int days) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada con ID: " + courtId));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(days - 1);

        log.info("Generando horarios para cancha específica: {} - {} días", court.getCourtName(), days);
        generateTimeslotsForCourt(court, startDate, endDate);
    }
}