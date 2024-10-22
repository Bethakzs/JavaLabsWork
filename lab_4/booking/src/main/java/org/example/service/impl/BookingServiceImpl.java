package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.BookingRepository;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.Hotel;
import org.example.entity.booking.Booking;
import org.example.entity.booking.UserDTO;
import org.example.exception.ApartmentNotFoundException;
import org.example.exception.ApartmentUnavailableForDateRangeException;
import org.example.exception.UserBalanceException;
import org.example.exception.UserNotFoundException;
import org.example.kafka.KafkaConsumerService;
import org.example.service.ApartmentService;
import org.example.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final ApartmentService apartmentService;
	private final KafkaConsumerService kafkaConsumerService;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Override
	@Transactional
	public Booking bookApartment(String email, Long apartmentId, LocalDate startDate, LocalDate endDate) {
		UserDTO user = findOrRetrieveUserByEmail(email);
		Apartment apartment = apartmentService.findApartmentById(apartmentId);

		if (apartment instanceof Hotel) {
			throw new IllegalArgumentException("Cannot book a whole hotel");
		}

		if (isApartmentAlreadyBookedInThisRange(apartment, startDate, endDate)) {
			throw new ApartmentUnavailableForDateRangeException(HttpStatus.BAD_REQUEST.value(),
					"Apartment is already booked in the given date range");
		}

		BigDecimal totalPrice = apartmentService.getTotalPriceOfApartment(apartment, startDate, endDate);
		if (user.getBalance().compareTo(totalPrice) < 0) {
			log.error("User balance is not enough. Total cost: {} and user balance: {}", totalPrice, user.getBalance());
			throw new UserBalanceException(HttpStatus.BAD_REQUEST.value(),
					"User balance is not enough to book this apartment");
		}

		user.setBalance(user.getBalance().subtract(totalPrice));
		Booking booking = new Booking(user, apartment, startDate, endDate, apartment.getPrice());
		log.info("Saving booking {}", booking);
		Booking savedBooking = bookingRepository.save(booking);

		String message = user.getEmail() + "," + totalPrice;
		kafkaTemplate.send("user-withdraw-balance-topic", message);
		return savedBooking;
	}

	private UserDTO findOrRetrieveUserByEmail(String email) {
		UserDTO kafkaUser = kafkaConsumerService.getUserFromKafka(email);
		if (kafkaUser != null) {
			log.info("User with email {}", kafkaUser.getEmail());
			return kafkaUser;
		} else {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND.value(), "User with email " + email + " not found");
		}
	}

	private boolean isApartmentAlreadyBookedInThisRange(Apartment apartment, LocalDate startDate, LocalDate endDate) {
		return bookingRepository.existsByApartmentAndStartDateBetween(apartment, startDate, endDate);
	}

	@Override
	public boolean isDateAvailable(Apartment apartment, LocalDate date) {
		List<Booking> bookings = bookingRepository.findByApartment(apartment);

		return bookings.stream()
				.noneMatch(booking -> {
					LocalDate start = booking.getStartDate();
					LocalDate end = booking.getEndDate();
					return !date.isBefore(start) && !date.isAfter(end);
				});
	}

	@Override
	public List<Booking> getBookingHistoryForApartment(Apartment apartment) {
		return bookingRepository.findByApartment(apartment);
	}

	@Override
	public boolean existsByApartment(Apartment apartment) {
		return bookingRepository.existsByApartment(apartment);
	}

	@Override
	@Transactional
	public void unBookApartment(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ApartmentNotFoundException(HttpStatus.NOT_FOUND.value(), "Booking with id " + bookingId + " not found"));
		bookingRepository.delete(booking);
	}
}
