package org.example.apartment.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.amenity.model.Amenity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hotel extends Apartment {
	private static final Logger LOGGER = LogManager.getLogger(Hotel.class);
	private final List<Room> rooms;

	public Hotel(String name, double price, Type type, List<Amenity> amenities, List<Room> rooms) {
		super(name, price, type, amenities);
		this.rooms = rooms;
	}

	@Override
	public void printInfo() {
		LOGGER.info("Hotel name: {}", super.getName());
		LOGGER.info("Hotel type: {}", super.getType());
		LOGGER.info("Hotel amenities: ");
		getAmenities().stream()
				.map(Amenity::getAmenityType)
				.forEach(amenityType -> LOGGER.info("Amenity: {}", amenityType));

		LOGGER.info("Rooms: ");
		rooms.forEach(LOGGER::info);
	}

	public void printRoomInfo(Room room) {
		LOGGER.info("Room name: {}", room.getName());
		LOGGER.info("Room max space for adults: {}", room.getMaxSpace());
		LOGGER.info("Room max space for children: {}", room.getChildrenMaxSpace());
		LOGGER.info("Room max space for animals: {}", room.getAnimalMaxSpace());

		Set<Amenity> uniqueAmenities = new HashSet<>(room.getAmenities());
		uniqueAmenities.addAll(getAmenities());

		if (uniqueAmenities.isEmpty()) {
			LOGGER.info("No amenities available for this room and hotel.\n");
		} else {
			LOGGER.info("Combined amenities: ");
			uniqueAmenities.stream()
					.map(Amenity::getAmenityType)
					.forEach(LOGGER::info);
		}
	}
}
