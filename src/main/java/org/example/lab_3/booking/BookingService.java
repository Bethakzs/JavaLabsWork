package org.example.lab_3.booking;

import org.example.lab_3.booking.model.Booking;
import org.example.lab_3.error.ApartmentAlreadyBooked;
import org.example.lab_3.apartment.model.Apartment;
import org.example.lab_3.consumer.model.Consumer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    private final Map<Consumer, Map<Apartment, LocalDate[]>> bookings = new HashMap<>();
    private final List<Booking> bookingHistory = new ArrayList<>();

    public void bookApartment(Consumer consumer, Apartment apartment, LocalDate startDate, LocalDate endDate) throws ApartmentAlreadyBooked {
        if (bookings.containsKey(consumer) && bookings.get(consumer).containsKey(apartment)) { // Check if the consumer has already booked the apartment
            throw new ApartmentAlreadyBooked("Apartment already booked for this consumer");
        }

        if (isApartmentAlreadyBooked(apartment, startDate, endDate)) { // Check if the apartment is already booked in the given date range
            throw new ApartmentAlreadyBooked("Apartment is already booked in the given date range");
        }

        Map<Apartment, LocalDate[]> consumerBookings = bookings.computeIfAbsent(consumer, _ -> new HashMap<>());
        consumerBookings.put(apartment, new LocalDate[]{startDate, endDate});

        double pricePerDay = apartment.getPrice();
        Booking booking = new Booking(consumer, apartment, startDate, endDate, pricePerDay);
        bookingHistory.add(booking);

        System.out.println("Apartment " + apartment.getName() + " booked for " + consumer.firstName() + " " + consumer.lastName() + " from " + startDate + " to " + endDate);
    }

    private boolean isApartmentAlreadyBooked(Apartment apartment, LocalDate startDate, LocalDate endDate) {
        return bookings.values().stream()
                .filter(consumerBookings -> consumerBookings.containsKey(apartment))
                .map(consumerBookings -> consumerBookings.get(apartment))
                .anyMatch(bookedDates -> startDate.isBefore(bookedDates[1]) && endDate.isAfter(bookedDates[0]));
    }

    public boolean isDateAvailable(Apartment apartment, LocalDate date) {
        return bookings.values().stream()
                .filter(consumerBookings -> consumerBookings.containsKey(apartment))
                .map(consumerBookings -> consumerBookings.get(apartment))
                .noneMatch(bookedDates -> !date.isBefore(bookedDates[0]) && !date.isAfter(bookedDates[1]));
    }

    public void printBookings() {
        bookings.forEach((consumer, apartmentBookings) ->
                apartmentBookings.forEach((apartment, dates) ->
                        System.out.println(consumer.firstName() + " " + consumer.lastName() + " booked " + apartment.getName() + " from " + dates[0] + " to " + dates[1])
                )
        );
    }

    public Booking getBookingHistoryForApartment(Apartment apartment) {
        return bookingHistory.stream()
                .filter(booking -> booking.getApartment().equals(apartment))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No bookings found for the apartment"));
    }

    // Getter
    public List<Booking> getBookingHistory() {
        return bookingHistory;
    }
}
