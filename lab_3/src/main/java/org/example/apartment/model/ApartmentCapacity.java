package org.example.apartment.model;

import org.example.amenity.model.Amenity;
import org.example.error.CategoryUnknownException;

import java.util.List;

public abstract class ApartmentCapacity extends Apartment {
	protected int maxSpace;
	protected int childrenMaxSpace;
	protected int animalMaxSpace;

	protected ApartmentCapacity(String name, int maxSpace, double price, Type type, List<Amenity> amenities) {
		super(name, price, type, amenities);
		this.maxSpace = maxSpace;
		this.childrenMaxSpace = 0;
		this.animalMaxSpace = 0;

		amenities.forEach(amenity -> {
			if (amenity.getCategory() != null) {
				switch (amenity.getCategory()) {
					case ADULT -> this.maxSpace += amenity.getAdditionalSpace();
					case CHILD -> this.childrenMaxSpace += amenity.getAdditionalSpace();
					case ANIMAL -> this.animalMaxSpace += amenity.getAdditionalSpace();
					default -> throw new CategoryUnknownException("Unknown category: " + amenity.getCategory());
				}
			}
		});
	}

	// Getters
	public int getMaxSpace() {
		return maxSpace;
	}

	public int getChildrenMaxSpace() {
		return childrenMaxSpace;
	}

	public int getAnimalMaxSpace() {
		return animalMaxSpace;
	}
}
