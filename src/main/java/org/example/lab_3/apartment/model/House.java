package org.example.lab_3.apartment.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public class House extends ApartmentCapacity {
	private static final Logger LOGGER = LogManager.getLogger(House.class);

	public House(String name, Type type, int maxSpace, double price, List<Amenity> amenities) {
		super(name, maxSpace, price, type, amenities);
	}

	@Override
	public void printInfo() {
		LOGGER.info("House name: {}", super.getName());
		LOGGER.info("House type: {}", super.getType());
		LOGGER.info("House max space for adults: {}", super.getMaxSpace());
		LOGGER.info("House max space for children: {}", super.getChildrenMaxSpace());
		LOGGER.info("House max space for animals: {}", super.getAnimalMaxSpace());

		LOGGER.info("House amenities: ");
		super.getAmenities().stream()
				.map(Amenity::getAmenityType)
				.forEach(amenityType -> LOGGER.info("Amenity: {}", amenityType));
	}
}
