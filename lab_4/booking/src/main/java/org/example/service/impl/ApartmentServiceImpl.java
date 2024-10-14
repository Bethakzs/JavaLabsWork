package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.ApartmentRepository;
import org.example.dao.BookingRepository;
import org.example.entity.amenity.AmenityType;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.Type;
import org.example.entity.booking.Booking;
import org.example.exception.ApartmentNotFoundException;
import org.example.service.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentServiceImpl implements ApartmentService {
	private static final double TAX = 0.30; // Include all taxes in our logic

	private final ApartmentRepository apartmentRepository;
	private final BookingRepository bookingRepository;

	@Override
	public List<Apartment> searchApartment(AmenityType amenityType, Type type) {
		return apartmentRepository.findAll().stream()
				.filter(apartment ->
						(amenityType == null || hasAmenity(apartment, amenityType)) &&
								(type == null || hasType(apartment, type))
				)
				.toList();
	}

	private boolean hasAmenity(Apartment apartment, AmenityType amenityType) {
		return apartment.getAmenities().stream()
				.anyMatch(amenity -> amenity.getAmenityType().equals(amenityType));
	}

	private boolean hasType(Apartment apartment, Type type) {
		return apartment.getType().equals(type);
	}

	@Override
	public String getStatistics(Long id) {
		Apartment apartment = apartmentRepository.findById(id).orElseThrow(
				() -> new ApartmentNotFoundException(HttpStatus.NOT_FOUND.value(), "Apartment not found"));

		BigDecimal totalIncome = calculateTotalIncome(apartment);
		BigDecimal totalCost = calculateTotalCostOfApartment(apartment);
		BigDecimal totalProfit = totalIncome.subtract(totalCost);

		return String.format("Total Income: %.2f, Total Cost: %.2f, Total Profit: %.2f",
				totalIncome, totalCost, totalProfit);
	}

	@Override
	public BigDecimal getTotalPriceOfApartment(Apartment apartment, LocalDate startDate, LocalDate endDate) {
		Apartment apartmentFromDb = apartmentRepository.findById(apartment.getId()).orElseThrow(
				() -> new ApartmentNotFoundException(HttpStatus.NOT_FOUND.value(), "Apartment not found"));

		BigDecimal pricePerDay = apartmentFromDb.getPrice();

		return calculateTotalCostOfApartment(startDate, endDate, pricePerDay);
	}

	@Override
	public Apartment findApartmentById(Long apartmentId) {
		return apartmentRepository.findById(apartmentId).orElseThrow(
				() -> new ApartmentNotFoundException(HttpStatus.NOT_FOUND.value(), "Apartment not found"));
	}

	private BigDecimal calculateTotalIncome(Apartment apartment) {
		List<Booking> bookings = bookingRepository.findByApartment(apartment);

		if (bookings.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return bookings.stream()
				.map(booking -> {
					BigDecimal bookingIncome = calculateTotalCostOfApartment(booking.getStartDate(), booking.getEndDate(), booking.getPricePerDay());
					BigDecimal amenitiesIncome = calculateAmenitiesIncome(booking);
					return bookingIncome.add(amenitiesIncome);
				})
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal calculateTotalCostOfApartment(LocalDate startDate, LocalDate endDate, BigDecimal pricePerDay) {
		BigDecimal totalCost = BigDecimal.ZERO;
		LocalDate currentDay = startDate;
		long totalDays = startDate.until(endDate).getDays();

		for (long i = 0; i < totalDays; i++) {
			BigDecimal dayCost = pricePerDay;

			if (currentDay.getMonthValue() == 3 || currentDay.getMonthValue() == 11) {
				dayCost = dayCost.multiply(BigDecimal.valueOf(0.8));
			}

			totalCost = totalCost.add(dayCost);
			currentDay = currentDay.plusDays(1);
		}

		return totalCost;
	}

	private BigDecimal calculateAmenitiesIncome(Booking booking) {
		return booking.getApartment().getAmenities().stream()
				.map(amenity -> BigDecimal.valueOf(amenity.getAmenityType().getValue()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calculateTotalCostOfApartment(Apartment apartment) {
		BigDecimal dailyAmenitiesCost = calculateDailyAmenitiesCost(apartment);

		BigDecimal totalAmenitiesCost = bookingRepository.findByApartment(apartment).stream()
				.map(booking -> dailyAmenitiesCost.multiply(BigDecimal.valueOf(booking.getDays())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal totalTaxCost = bookingRepository.findByApartment(apartment).stream()
				.map(booking ->
						BigDecimal.valueOf(booking.getDays()).multiply(
								apartment.getPrice().multiply(BigDecimal.valueOf(TAX))
						)
				)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return totalTaxCost.add(totalAmenitiesCost);
	}

	private BigDecimal calculateDailyAmenitiesCost(Apartment apartment) {
		return apartment.getAmenities().stream()
				.map(amenity -> switch (amenity.getAmenityType()) {
					case WI_FI -> BigDecimal.valueOf(5);
					case SWIMMING_POOL -> BigDecimal.valueOf(10);
					case SPA -> BigDecimal.valueOf(8);
					case FOOTBALL_STADIUM -> BigDecimal.valueOf(15);
					default -> BigDecimal.ZERO;
				})
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

