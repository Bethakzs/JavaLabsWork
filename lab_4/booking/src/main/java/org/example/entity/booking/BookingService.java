package org.example.entity.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.apartment.Apartment;
import org.example.entity.booking.exception.ApartmentUnavailableForDateRangeException;
import org.example.entity.booking.exception.UserNotFoundException;
import org.example.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingService {

	private final BookingRepository bookingRepository;
	private final UserDTORepository userDTORepository;
	private final HouseService apartmentService;
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

		Apartment apartment = apartmentService.findHouseById(apartmentId);
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
