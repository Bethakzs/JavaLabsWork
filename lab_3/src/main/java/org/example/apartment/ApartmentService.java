package org.example.apartment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.amenity.model.AmenityType;
import org.example.apartment.model.Apartment;
import org.example.apartment.model.Type;
import org.example.booking.model.Booking;

import java.util.List;

public class ApartmentService {
	private static final Logger LOGGER = LogManager.getLogger(ApartmentService.class);
	private static final double TAX = 0.30; // Include all taxes in our logic

	private final List<Apartment> apartments;
	private final List<Booking> bookings;

	public ApartmentService(List<Apartment> apartments, List<Booking> bookings) {
		this.apartments = apartments;
		this.bookings = bookings;
	}

	public List<Apartment> searchApartment(AmenityType amenityType, Type type) {
		return apartments.stream()
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

	public void printStatistics(Apartment house1) {
		double totalIncome = calculateTotalIncome(house1);
		double totalCost = calculateTotalCost(house1);
		double totalProfit = totalIncome - totalCost;

		LOGGER.info("Total Income: {}", totalIncome);
		LOGGER.info("Total Cost: {}", totalCost);
		LOGGER.info("Total Profit: {}", totalProfit);
	}

	private double calculateTotalIncome(Apartment apartment) {
		double allCostOnAmenities = apartment.getAmenities().stream()
				.mapToDouble(amenity -> amenity.getAmenityType().getValue())
				.sum();

		double allCostOnBookings = bookings.stream()
				.filter(booking -> booking.getApartment().equals(apartment))
				.mapToDouble(Booking::getTotalPrice)
				.sum();

		return allCostOnAmenities + allCostOnBookings;
	}

	private double calculateTotalCost(Apartment apartment) {
		double dailyAmenitiesCost = calculateDailyAmenitiesCost(apartment);

		double totalAmenitiesCost = bookings.stream()
				.filter(booking -> booking.getApartment().equals(apartment))
				.mapToDouble(booking -> dailyAmenitiesCost * booking.getDays())
				.sum();

		double totalTaxCost = bookings.stream()
				.filter(booking -> booking.getApartment().equals(apartment))
				.mapToDouble(booking -> booking.getDays() * apartment.getPrice() * TAX)
				.sum();

		return totalTaxCost + totalAmenitiesCost;
	}

	private double calculateDailyAmenitiesCost(Apartment apartment) {
		return apartment.getAmenities().stream()
				.mapToDouble(amenity -> switch (amenity.getAmenityType()) {
					case WI_FI -> 5;
					case SWIMMING_POOL -> 10;
					case SPA -> 8;
					case FOOTBALL_STADIUM -> 15;
					default -> 0;
				})
				.sum();
	}
}
