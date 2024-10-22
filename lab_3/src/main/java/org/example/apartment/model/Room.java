package org.example.apartment.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.amenity.model.Amenity;

import java.util.List;

public class Room extends ApartmentCapacity {
	private static final Logger LOGGER = LogManager.getLogger(Room.class);

	public Room(String name, Type type, int maxSpace, double price, List<Amenity> amenities) {
		super(name, maxSpace, price, type, amenities);
	}

	@Override
	public void printInfo() {
		LOGGER.info("Room name: {}", super.getName());
		LOGGER.info("Room type: {}", super.getType());
		LOGGER.info("Room max space for adults: {}", super.getMaxSpace());
		LOGGER.info("Room max space for children: {}", super.getChildrenMaxSpace());
		LOGGER.info("Room max space for animals: {}", super.getAnimalMaxSpace());

		LOGGER.info("Room amenities: ");
		super.getAmenities().stream()
				.map(Amenity::getAmenityType)
				.forEach(amenityType -> LOGGER.info("Amenity: {}", amenityType));
		LOGGER.info("");
	}

	@Override
	public String toString() {
		return getName();
	}
}
