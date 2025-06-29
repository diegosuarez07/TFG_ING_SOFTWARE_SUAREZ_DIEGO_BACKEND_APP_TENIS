package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.Timeslot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

    // Verificar si existen horarios para una cancha y fecha
    boolean existsByCourtIdAndDate(Long courtId, LocalDate date);

    // Buscar horarios disponibles por cancha y fecha
    List<Timeslot> findByCourtIdAndDateAndStatus(Long courtId, LocalDate date, String status);

    // Buscar horarios disponibles por fecha
    List<Timeslot> findByDateAndStatus(LocalDate date, String status);

    // Buscar horarios por cancha
    List<Timeslot> findByCourtId(Long courtId);

    // Buscar horarios disponibles
    List<Timeslot> findByStatus(String status);

    @Modifying
    @Query("UPDATE Timeslot t SET t.status = :status WHERE t.timeslotId = :timeslotId")
    void updateStatus(@Param("timeslotId") Long timeslotId, @Param("status") String status);

    @Modifying
    @Query("DELETE FROM Timeslot t WHERE t.date < :date")
    @Transactional
    int deleteByDateBefore(@Param("date") LocalDate date);
}