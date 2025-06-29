package com.siglo21.tfg.repository;

import com.siglo21.tfg.entity.CourtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {

    List<CourtBooking> findByUserId(Long userId);

    List<CourtBooking> findByTimeslotId(Long timeslotId);

    Optional<CourtBooking> findByTimeslotIdAndStatus(Long timeslotId, String status);

    @Query("SELECT cb FROM CourtBooking cb WHERE cb.userId = :userId AND cb.status = :status")
    List<CourtBooking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);

    @Query("SELECT cb FROM CourtBooking cb WHERE cb.timeslotId = :timeslotId AND cb.status IN ('CONFIRMED', 'PENDING')")
    Optional<CourtBooking> findActiveBookingByTimeslotId(@Param("timeslotId") Long timeslotId);

    @Query("SELECT cb FROM CourtBooking cb " +
            "JOIN FETCH cb.timeslot t " +
            "JOIN FETCH t.court " +
            "WHERE cb.bookingId = :bookingId")
    Optional<CourtBooking> findByIdWithRelations(@Param("bookingId") Long bookingId);
}