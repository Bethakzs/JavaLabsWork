package org.example.lab_3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger LOGGER = LogManager.getLogger(WorkClass.class);
	private static final String OPERATOR = "\n\n\n";

	public static void main(String[] args) {
		TestResources.house3.printInfo();
		LOGGER.info(OPERATOR);
		TestResources.hotel1.printInfo();
		LOGGER.info(OPERATOR);
		TestResources.hotel1.printRoomInfo(TestResources.room1); // subtask 4
		LOGGER.info(OPERATOR);

		BookingService bookingService = new BookingService();

		TestResources.checkIsDateAvailable(bookingService, TestResources.house1, TestResources.bookingDate1); // subtask 6
		try {
			bookingService.bookApartment(TestResources.consumer1, TestResources.house1, // subtask 7
					TestResources.bookingDate1, TestResources.bookingDate2);
			bookingService.bookApartment(TestResources.consumer1, TestResources.house1,
					TestResources.bookingDate1, TestResources.bookingDate1);
		} catch (ApartmentAlreadyBooked e) { // subtask 6
			LOGGER.error(e.getMessage());
		}
		TestResources.checkIsDateAvailable(bookingService, TestResources.house1, TestResources.bookingDate1);

		bookingService.printBookings();

		try {
			LOGGER.info(bookingService.getBookingHistoryForApartment(TestResources.house1)); // subtask additional
			LOGGER.info(bookingService.getBookingHistoryForApartment(TestResources.house2));
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage());
		}

		LOGGER.info(OPERATOR);

		ApartmentService apartmentService = new ApartmentService(TestResources.getApartments(), bookingService.getBookingHistory());

		apartmentService.printStatistics(TestResources.house1); // 2000 * 0,8 = 1600 * 6 = 9600 + 2000 = 11600 subtask 8

		List<Apartment> foundApartments = apartmentService.searchApartment(AmenityType.WI_FI, Type.LUXURY); // subtask 10
		foundApartments.forEach(apartment -> LOGGER.info(apartment.getName()));
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
			if (bookingService.isDateAvailable(apartment, date)) {
				LOGGER.info("Apartment {} is available on {}", apartment.getName(), date);
			} else {
				LOGGER.error("Apartment {} is NOT available on {}", apartment.getName(), date);
			}
		}

		private TestResources() {
			throw new IllegalStateException("Utility class");
		}
	}
}
