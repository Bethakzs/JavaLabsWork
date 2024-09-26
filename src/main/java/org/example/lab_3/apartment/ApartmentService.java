package org.example.lab_3.apartment;

import org.example.lab_3.amenity.model.AmenityType;
import org.example.lab_3.apartment.model.Apartment;
import org.example.lab_3.apartment.model.Type;
import org.example.lab_3.booking.model.Booking;
import java.util.List;
import java.util.stream.Collectors;

public class ApartmentService {
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
                        (amenityType == null || hasAmenity(apartment, amenityType))
                )
                .collect(Collectors.toList());
    }

    private boolean hasAmenity(Apartment apartment, AmenityType amenityType) {
        return apartment.getAmenities().stream()
                .anyMatch(amenity -> amenity.getAmenityType().equals(amenityType));
    }

//    private boolean HasType(Apartment apartment, Type type) {
//        return apartment.getAmenities().stream()
//                .anyMatch(amenity -> amenity.getType() != null && amenity.getType().equals(type));
//    }

    public void printStatistics(Apartment house1) {
        double totalIncome = calculateTotalIncome(house1);
        double totalCost = calculateTotalCost(house1);
        double totalProfit = totalIncome - totalCost;

        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Waste: " + totalCost);
        System.out.println("Total Profit: " + totalProfit);
        System.out.println();
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
        double dailyAmenitiesCost = apartment.getAmenities().stream()
                .mapToDouble(amenity -> {
                    AmenityType type = amenity.getAmenityType();
                    return switch (type) {
                        case WI_FI -> 5;
                        case SWIMMING_POOL -> 10;
                        case SPA -> 8;
                        case FOOTBALL_STADIUM -> 15;
                        default -> 0;
                    };
                })
                .sum();

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
}
