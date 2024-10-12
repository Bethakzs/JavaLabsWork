package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.BookingRepository;
import org.example.dao.UserDTORepository;
import org.example.entity.apartment.Apartment;
import org.example.entity.booking.Booking;
import org.example.entity.booking.UserDTO;
import org.example.exception.ApartmentUnavailableForDateRangeException;
import org.example.exception.UserNotFoundException;
import org.example.kafka.KafkaConsumerService;
import org.example.service.ApartmentService;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final UserDTORepository userDTORepository;
	private final ApartmentService apartmentService;
	private final KafkaConsumerService kafkaConsumerService;

	public Booking bookApartment(String email, Long apartmentId, LocalDate startDate, LocalDate endDate) {
		UserDTO user = userDTORepository.findByEmail(email).orElseGet(() -> {
			UserDTO kafkaUser = getUserFromKafka(email);
			if (kafkaUser != null) {
				UserDTO userDTO = userDTORepository.save(kafkaUser);
				log.info("User with email {} saved to the database", userDTO.getEmail());
				return userDTO;
			} else {
				throw new UserNotFoundException(HttpStatus.NOT_FOUND.value(), "User with email " + email + " not found");
			}
		});

		Apartment apartment = apartmentService.findApartmentById(apartmentId);
		if (isApartmentAlreadyBookedInThisRange(apartment, startDate, endDate)) {
			throw new ApartmentUnavailableForDateRangeException(HttpStatus.BAD_REQUEST.value(),
					"Apartment is already booked in the given date range");
		}

		Booking booking = new Booking(user, apartment, startDate, endDate, apartment.getPrice());
		return bookingRepository.save(booking);
	}

	private boolean isApartmentAlreadyBookedInThisRange(Apartment apartment, LocalDate startDate, LocalDate endDate) {
		return bookingRepository.existsByApartmentAndStartDateBetween(apartment, startDate, endDate);
	}

	public boolean isDateAvailable(Apartment apartment, LocalDate date) {
		List<Booking> bookings = bookingRepository.findByApartment(apartment);

		return bookings.stream()
				.noneMatch(booking -> {
					LocalDate start = booking.getStartDate();
					LocalDate end = booking.getEndDate();
					return !date.isBefore(start) && !date.isAfter(end);
				});
	}

	public List<Booking> getBookingHistoryForApartment(Apartment apartment) {
		return bookingRepository.findByApartment(apartment);
	}

	public UserDTO getUserFromKafka(String email) {
		return kafkaConsumerService.getUserFromKafka(email);
	}
}
