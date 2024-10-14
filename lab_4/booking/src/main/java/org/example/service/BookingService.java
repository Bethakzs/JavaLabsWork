package org.example.service;

import org.example.entity.apartment.Apartment;
import org.example.entity.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

	Booking bookApartment(String email, Long apartmentId, LocalDate startDate, LocalDate endDate);

	boolean isDateAvailable(Apartment apartment, LocalDate date);

	List<Booking> getBookingHistoryForApartment(Apartment apartment);

	boolean existsByApartment(Apartment apartment);

	void unBookApartment(Long bookingId);
}

