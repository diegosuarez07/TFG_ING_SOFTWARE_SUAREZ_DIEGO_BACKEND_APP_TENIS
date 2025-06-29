package com.siglo21.tfg.controller;

import com.siglo21.tfg.dto.request.CourtBookingRequestDto;
import com.siglo21.tfg.dto.response.CourtBookingResponseDto;
import com.siglo21.tfg.service.CourtBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/courtbookings")
@CrossOrigin(origins = "*")
public class CourtBookingController {

    private final CourtBookingService courtBookingService;

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<CourtBookingResponseDto> createBooking(@RequestBody CourtBookingRequestDto requestDto) {
        log.info("Solicitud para crear reserva: {}", requestDto);
        CourtBookingResponseDto response = courtBookingService.createBooking(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todas las reservas de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CourtBookingResponseDto>> getUserBookings(@PathVariable Long userId) {
        log.info("Solicitud para obtener reservas del usuario: {}", userId);
        log.info("Usuario autenticado: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<CourtBookingResponseDto> bookings = courtBookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    // Cancelar una reserva
    @PutMapping("/{bookingId}/cancel")
    @Transactional
    public ResponseEntity<CourtBookingResponseDto> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam Long userId) {
        log.info("Solicitud para cancelar reserva: {} por usuario: {}", bookingId, userId);
        CourtBookingResponseDto cancelledBooking = courtBookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok(cancelledBooking);
    }

}
