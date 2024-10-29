package org.example.service.impl;

import org.example.dao.BookingRepository;
import org.example.entity.amenity.Amenity;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.House;
import org.example.entity.booking.Booking;
import org.example.entity.booking.UserDTO;
import org.example.exception.ApartmentNotFoundException;
import org.example.kafka.KafkaConsumerService;
import org.example.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.entity.amenity.AmenityType.*;
import static org.example.entity.amenity.Category.ADULT;
import static org.example.entity.amenity.Category.CHILD;
import static org.example.entity.apartment.Type.LUXURY;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private ApartmentService apartmentService;

	@InjectMocks
	private BookingServiceImpl bookingService;

	@Mock
	private KafkaConsumerService kafkaConsumerService;

	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;

	@Test
	public void givenValidApartment_bookApartment_shouldReturnBooking() {
		// Given
		UserDTO user = TestResource.createUser();
		Apartment apartment = TestResource.createApartment();
		BigDecimal totalPrice = BigDecimal.valueOf(2000);

		// When
		bookTestApartment(user, apartment, TestResource.startValidDate, TestResource.endValidDate, totalPrice);

		// Then
		ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
		verify(bookingRepository).save(bookingCaptor.capture());

		Booking capturedBooking = bookingCaptor.getValue();
		assertThat(capturedBooking).isNotNull();
		assertThat(capturedBooking.getApartment()).isEqualTo(apartment);
		assertThat(capturedBooking.getStartDate()).isEqualTo(TestResource.startValidDate);
		assertThat(capturedBooking.getEndDate()).isEqualTo(TestResource.endValidDate);

		verify(kafkaTemplate).send(eq("user-withdraw-balance-topic"),
				argThat(message -> message.contains(user.getEmail()) && message.contains(totalPrice.toString())));
	}

	@Test
	public void givenValidApartment_isDateAvailable_shouldReturnListOfBooking() {
		// Given
		Apartment apartment = TestResource.createApartment();
		LocalDate dateToCheck = LocalDate.now();

		// When
		when(bookingRepository.findByApartment(apartment)).thenReturn(Collections.emptyList());

		boolean isAvailable = bookingService.isDateAvailable(apartment, dateToCheck);

		// Then
		assertThat(isAvailable).isTrue();
	}

	@Test
	public void givenValidApartment_getBookingHistoryForApartment_shouldReturnListOfBooking() {
		// Given
		Apartment apartment = TestResource.createApartment();
		Booking booking = new Booking(TestResource.createUser(), apartment,
				TestResource.startValidDate, TestResource.endValidDate, apartment.getPrice());

		// When
		when(bookingRepository.findByApartment(apartment)).thenReturn(List.of(booking));

		List<Booking> bookingHistory = bookingService.getBookingHistoryForApartment(apartment);

		// Then
		assertThat(bookingHistory).isNotEmpty();
		assertThat(bookingHistory).contains(booking);
	}

	@Test
	public void givenValidApartment_getBookingHistoryForApartment_shouldReturnEmptyListOfBooking() {
		// Given
		Apartment apartment = TestResource.createApartment();

		// When
		List<Booking> bookingHistory = bookingService.getBookingHistoryForApartment(apartment);

		// Then
		assertThat(bookingHistory).isEmpty();
	}

	@Test
	public void givenValidApartment_existsByApartment_shouldReturnTrue() {
		// Given
		Apartment apartment = TestResource.createApartment();

		// When
		when(bookingRepository.existsByApartment(apartment)).thenReturn(true);
		boolean exists = bookingService.existsByApartment(apartment);

		// Then
		assertThat(exists).isTrue();
		verify(bookingRepository).existsByApartment(apartment);
	}

	@Test
	public void givenValidApartment_existsByApartment_shouldReturnFalse() {
		// Given
		Apartment apartment = TestResource.createApartment();

		// When
		when(bookingRepository.existsByApartment(apartment)).thenReturn(false);
		boolean existApartment = bookingService.existsByApartment(apartment);

		// Then
		assertThat(existApartment).isFalse();
		verify(bookingRepository).existsByApartment(apartment);
	}

	@Test
	public void givenValidApartment_unBookApartment_shouldDeleteApartment() {
		// Given
		Long bookingId = 1L;
		Booking booking = new Booking();
		booking.setId(bookingId);

		// When
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		bookingService.unBookApartment(bookingId);

		// Then
		verify(bookingRepository).delete(booking);
	}

	@Test
	void givenNotExistApartment_unBookApartment_ThrowNotFoundException() {
		// Given
		Long bookingId = 1L;

		// When
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

		// Then
		assertThrows(ApartmentNotFoundException.class, () -> bookingService.unBookApartment(bookingId));
		verify(bookingRepository, never()).delete(any(Booking.class));
	}

	private void bookTestApartment(UserDTO user, Apartment apartment, LocalDate startValidDate, LocalDate endValidDate, BigDecimal totalPrice) {
		when(kafkaConsumerService.getUserFromKafka(user.getEmail())).thenReturn(user);
		lenient().when(apartmentService.findApartmentById(apartment.getId())).thenReturn(apartment);
		when(apartmentService.getTotalPriceOfApartment(apartment, startValidDate, endValidDate))
				.thenReturn(totalPrice);

		// Act
		bookingService.bookApartment(user.getEmail(), apartment.getId(), startValidDate, endValidDate);
	}

	private static class TestResource {
		private static final LocalDate startValidDate = LocalDate.now();
		private static final LocalDate endValidDate = LocalDate.now().plusDays(3);

		private static final List<Amenity> AMENITIES = List.of(new Amenity(1L, WI_FI, 0, ADULT),
				new Amenity(2L, SOFA, 1, ADULT), new Amenity(3L, CHILD_BED, 1, CHILD));

		static UserDTO createUser() {
			return new UserDTO(1L, "testFirstName", "testLastName",
					"test@gmail.com", BigDecimal.valueOf(2000));
		}

		static Apartment createApartment() {
			return new House("testApartment", BigDecimal.valueOf(2000),
					LUXURY, AMENITIES, 7);
		}
	}
}
