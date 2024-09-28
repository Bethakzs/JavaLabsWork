package org.example.lab_3.booking.model;

import org.example.lab_3.apartment.model.Apartment;
import org.example.lab_3.consumer.model.Consumer;

import java.time.LocalDate;

public class Booking {
	private static final int MARCH = 3; // Березень
	private static final int NOVEMBER = 11;
	private static final double DISCOUNT = 0.8;

	private final Consumer consumer;
	private final Apartment apartment;
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final int days;
	private final double pricePerDay;

	public Booking(Consumer consumer, Apartment apartment, LocalDate startDate, LocalDate endDate, double pricePerDay) {
		this.consumer = consumer;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = (int) (endDate.toEpochDay() - startDate.toEpochDay());
		this.pricePerDay = pricePerDay;
	}

	public double getTotalPrice() {
		double totalCost = 0.0;
		LocalDate currentDay = startDate;

		while (!currentDay.isAfter(endDate)) {
			double dayCost = pricePerDay;

			if (currentDay.getMonthValue() == MARCH || currentDay.getMonthValue() == NOVEMBER) {
				dayCost *= DISCOUNT;
			}

			totalCost += dayCost;
			currentDay = currentDay.plusDays(1); // Move to the next day like index
		}

		return totalCost;
	}

	// Getters
	public Apartment getApartment() {
		return apartment;
	}

	public int getDays() {
		return days;
	}

	@Override
	public String toString() {
		return "Booking |" +
				"consumer=" + consumer +
				", apartment=" + apartment +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", days=" + days +
				", pricePerDay=" + pricePerDay +
				'|' + '\n';
	}
}
