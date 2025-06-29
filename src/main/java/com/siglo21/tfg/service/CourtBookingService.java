package com.siglo21.tfg.service;

import com.siglo21.tfg.dto.request.CourtBookingRequestDto;
import com.siglo21.tfg.dto.response.CourtBookingResponseDto;

import java.util.List;

public interface CourtBookingService {

    CourtBookingResponseDto createBooking(CourtBookingRequestDto requestDto);

    List<CourtBookingResponseDto> getUserBookings(Long userId);

    CourtBookingResponseDto getBookingById(Long bookingId);

    CourtBookingResponseDto cancelBooking(Long bookingId, Long userId);

    List<CourtBookingResponseDto> getAllBookings();

}
