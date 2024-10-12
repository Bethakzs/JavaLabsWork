package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.apartment.Apartment;
import org.example.entity.booking.Booking;
import org.example.dto.request.BookingRequestDTO;
import org.example.service.impl.BookingServiceImpl;
import org.example.service.HouseService;
import org.example.util.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("v1/api/booking")
@RequiredArgsConstructor
public class BookingController {

	private final BookingServiceImpl bookingServiceImpl;
	private final HouseService apartmentService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/book")
	public ResponseEntity<Booking> bookApartment(@Valid @RequestBody BookingRequestDTO bookingRequest,
												 @RequestHeader("Authorization") String authHeader) {
		Booking booking = bookingServiceImpl.bookApartment(
				parseAuthorizationHeader(authHeader),
				bookingRequest.getApartmentId(),
				bookingRequest.getStartDate(),
				bookingRequest.getEndDate());
		return ResponseEntity.ok(booking);
	}

	@GetMapping("/availability/{apartmentId}/{date}")
	public ResponseEntity<Boolean> isDateAvailable(@PathVariable Long apartmentId, @PathVariable LocalDate date) {
		Apartment apartment = apartmentService.findHouseById(apartmentId);
		boolean available = bookingServiceImpl.isDateAvailable(apartment, date);
		return ResponseEntity.ok(available);
	}

	@GetMapping("/history/{apartmentId}")
	public ResponseEntity<List<Booking>> getBookingHistory(@PathVariable Long apartmentId) {
		Apartment apartment = apartmentService.findHouseById(apartmentId);
		List<Booking> bookingHistory = bookingServiceImpl.getBookingHistoryForApartment(apartment);
		if (bookingHistory.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bookingHistory);
	}

	private String parseAuthorizationHeader(String authHeader) {
		String token = authHeader.substring(7);
		return jwtTokenProvider.getEmailFromToken(token);
	}
}
