package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.CourtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {

    // Buscar reservas por usuario
    List<CourtBooking> findByUserId(Long userId);

    // Buscar reservas por timeslot
    List<CourtBooking> findByTimeslotId(Long timeslotId);

    // Buscar reservas por estado
    List<CourtBooking> findByStatus(String status);
}