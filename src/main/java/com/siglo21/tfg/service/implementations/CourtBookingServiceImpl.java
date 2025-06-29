package com.siglo21.tfg.service.implementations;

import com.siglo21.tfg.dto.request.CourtBookingRequestDto;
import com.siglo21.tfg.dto.response.CourtBookingResponseDto;
import com.siglo21.tfg.entity.CourtBooking;
import com.siglo21.tfg.entity.Timeslot;
import com.siglo21.tfg.enums.TimeslotStatus;
import com.siglo21.tfg.repository.CourtBookingRepository;
import com.siglo21.tfg.repository.TimeslotRepository;
import com.siglo21.tfg.service.CourtBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtBookingServiceImpl implements CourtBookingService {

    private final CourtBookingRepository courtBookingRepository;
    private final TimeslotRepository timeslotRepository;

    @Override
    @Transactional
    public CourtBookingResponseDto createBooking(CourtBookingRequestDto requestDto) {
        log.info("Creando reserva para usuario: {} y timeslot: {}", requestDto.getUserId(), requestDto.getTimeslotId());

        // Verificar que el timeslot existe y está disponible
        Timeslot timeslot = timeslotRepository.findById(requestDto.getTimeslotId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Timeslot no encontrado"));

        if (!TimeslotStatus.AVAILABLE.getDbValue().equals(timeslot.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El timeslot no está disponible para reserva");
        }

        // Verificar que no hay una reserva activa para este timeslot
        courtBookingRepository.findActiveBookingByTimeslotId(requestDto.getTimeslotId())
                .ifPresent(booking -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una reserva activa para este timeslot");
                });

        // Crear la reserva
        CourtBooking booking = new CourtBooking();
        booking.setUserId(requestDto.getUserId());
        booking.setTimeslotId(requestDto.getTimeslotId());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        CourtBooking savedBooking = courtBookingRepository.save(booking);

        // Actualizar el estado del timeslot a reservado
        timeslotRepository.updateStatus(requestDto.getTimeslotId(), TimeslotStatus.BOOKED.getDbValue());

        log.info("Reserva creada exitosamente con ID: {}", savedBooking.getBookingId());

        // Vuelve a buscar la reserva para traer las relaciones completas
        CourtBooking bookingWithRelations = courtBookingRepository.findByIdWithRelations(savedBooking.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la reserva creada"));

        return bookingToDto(bookingWithRelations);
    }

    @Override
    public List<CourtBookingResponseDto> getUserBookings(Long userId) {
        log.info("Obteniendo reservas del usuario: {}", userId);

        List<CourtBooking> bookings = courtBookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::bookingToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourtBookingResponseDto getBookingById(Long bookingId) {
        log.info("Obteniendo reserva con ID: {}", bookingId);

        CourtBooking booking = courtBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva no encontrada"));

        return bookingToDto(booking);
    }

    @Override
    public CourtBookingResponseDto cancelBooking(Long bookingId, Long userId) {
        log.info("Cancelando reserva: {} por usuario: {}", bookingId, userId);

        CourtBooking booking = courtBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reserva no encontrada"));

        // Verificar que el usuario es el propietario de la reserva
        if (!booking.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tienes permisos para cancelar esta reserva");
        }

        // Verificar que la reserva no esté ya cancelada
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La reserva ya está cancelada");
        }

        // Cancelar la reserva
        booking.setStatus("CANCELLED");
        CourtBooking savedBooking = courtBookingRepository.save(booking);

        // Liberar el timeslot (volver a disponible)
        timeslotRepository.updateStatus(booking.getTimeslotId(), TimeslotStatus.AVAILABLE.getDbValue());

        log.info("Reserva cancelada exitosamente");

        return bookingToDto(savedBooking);
    }

    @Override
    public List<CourtBookingResponseDto> getAllBookings() {
        log.info("Obteniendo todas las reservas");

        List<CourtBooking> bookings = courtBookingRepository.findAll();

        return bookings.stream()
                .map(this::bookingToDto)
                .collect(Collectors.toList());
    }

    private CourtBookingResponseDto bookingToDto(CourtBooking booking) {
        CourtBookingResponseDto dto = new CourtBookingResponseDto();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUserId());
        dto.setTimeslotId(booking.getTimeslotId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dto.setBookingDate(booking.getBookingDate().format(formatter));
        dto.setStatus(booking.getStatus());

        // Obtener información del timeslot si está disponible
        if (booking.getTimeslot() != null) {
            Timeslot timeslot = booking.getTimeslot();
            dto.setDate(timeslot.getDate().toString());
            dto.setStartTime(timeslot.getStartTime().toString().substring(0, 5));
            dto.setEndTime(timeslot.getEndTime().toString().substring(0, 5));

            if (timeslot.getCourt() != null) {
                dto.setCourtName(timeslot.getCourt().getCourtName());
                dto.setCourtNumber(timeslot.getCourt().getCourtNumber().toString());
            }
        }

        return dto;
    }
}