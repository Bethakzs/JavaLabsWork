package org.example.lab_3;

import org.example.lab_3.amenity.model.Amenity;
import org.example.lab_3.amenity.model.AmenityType;
import org.example.lab_3.apartment.ApartmentService;
import org.example.lab_3.apartment.model.*;
import org.example.lab_3.booking.BookingService;
import org.example.lab_3.consumer.model.Consumer;
import org.example.lab_3.error.ApartmentAlreadyBooked;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.lab_3.amenity.model.Category.*;

public class WorkClass {

	private static final String OPERATOR = "--------------------------------------------------";

	public static void main(String[] args) {
		// Amenity
		TestResources.house3.printInfo();
		System.out.println(OPERATOR);
		TestResources.hotel1.printInfo();
		System.out.println(OPERATOR);
		TestResources.hotel1.printRoomInfo(TestResources.room1);
		System.out.println(OPERATOR);

		// BookingService
		BookingService bookingService = new BookingService();

		TestResources.checkIsDateAvailable(bookingService, TestResources.house1, TestResources.bookingDate1);
		try {
			bookingService.bookApartment(TestResources.consumer1, TestResources.house1, TestResources.bookingDate1, TestResources.bookingDate2);
			bookingService.bookApartment(TestResources.consumer1, TestResources.house1, TestResources.bookingDate1, TestResources.bookingDate1);
		} catch (ApartmentAlreadyBooked e) {
			System.err.println(e.getMessage());
		}
		TestResources.checkIsDateAvailable(bookingService, TestResources.house1, TestResources.bookingDate1);

		bookingService.printBookings();

		try {
			System.out.println(bookingService.getBookingHistoryForApartment(TestResources.house1));
			System.out.println(bookingService.getBookingHistoryForApartment(TestResources.house2));
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(OPERATOR);

		// ApartmentService
		ApartmentService apartmentService = new ApartmentService(TestResources.getApartments(), bookingService.getBookingHistory());

		apartmentService.printStatistics(TestResources.house1); // 2000 * 0,8 = 1600 * 6 = 9600 + 4000 = 13600

		List<Apartment> foundApartments = apartmentService.searchApartment(AmenityType.WI_FI, Type.LUXURY);
		foundApartments.forEach(apartment -> System.out.println(apartment.getName()));
	}

	static class TestResources {
		private static final Amenity amenity1 = new Amenity(AmenityType.WI_FI);
		private static final Amenity amenity2 = new Amenity(AmenityType.KITCHEN);
		private static final Amenity amenity3 = new Amenity(AmenityType.BATHROOM);
		private static final Amenity amenity4 = new Amenity(AmenityType.SECOND_BATHROOM);
		private static final Amenity amenity5 = new Amenity(AmenityType.SPA);
		private static final Amenity amenity6 = new Amenity(AmenityType.FOOTBALL_STADIUM);
		private static final Amenity amenity7 = new Amenity(AmenityType.SWIMMING_POOL);
		private static final Amenity amenity8 = new Amenity(AmenityType.SOFA, 1, ADULT);
		private static final Amenity amenity9 = new Amenity(AmenityType.DOUBLE_SOFA, 2, ADULT);
		private static final Amenity amenity10 = new Amenity(AmenityType.CHILD_BED, 1, CHILD);
		private static final Amenity amenity11 = new Amenity(AmenityType.DOG_HOUSE, 1, ANIMAL);

		private static final Apartment house1 = new House("House 1", Type.ECONOMIC, 3, 2000,
				new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity5, amenity9)));
		private static final Apartment house2 = new House("House 2", Type.STANDARD, 6, 2500,
				new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity5, amenity8)));
		private static final Apartment house3 = new House("House 3", Type.LUXURY, 10, 5000,
				new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity4, amenity5, amenity6, amenity7, amenity10)));
		private static final Apartment house4 = new House("House 4", Type.LUXURY, 3, 7000,
				new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity4, amenity5, amenity6, amenity7, amenity11)));

		private static final Room room1 = new Room("Room 1", Type.APARTMENT, 3, 800,
				new ArrayList<>(List.of(amenity1, amenity2, amenity10)));
		private static final Room room2 = new Room("Room 2", Type.APARTMENT, 4, 1000,
				new ArrayList<>(List.of(amenity1, amenity4, amenity9)));
		private static final Room room3 = new Room("Room 3", Type.APARTMENT, 3, 1500,
				new ArrayList<>());

		private static final Hotel hotel1 = new Hotel("Hotel 1", 5000, Type.LUXURY,
				new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity11)),
				new ArrayList<>(List.of(room1, room2, room3)));

		private static final Consumer consumer1 = new Consumer("Ivan", "Ivanenko", "+380123456789", "ivan@gmail.com");

		private static final LocalDate bookingDate1 = LocalDate.parse("2024-03-26");
		private static final LocalDate bookingDate2 = LocalDate.parse("2024-04-02");

		private static List<Apartment> getApartments() {
			return List.of(house1, house2, house3, house4
					, room1, room2, room3
					, hotel1);
		}

		private static void checkIsDateAvailable(BookingService bookingService, Apartment apartment, LocalDate date) {
			if (bookingService.isDateAvailable(TestResources.house1, date)) {
				System.out.println("Apartment " + apartment.getName() + " is available on " + date);
			} else {
				System.out.println("Apartment " + apartment.getName() + " is not available on " + date);
			}
		}

		private TestResources() {
			throw new IllegalStateException("Utility class");
		}
	}
}