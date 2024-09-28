package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public abstract class Apartment {
	private final String name;
	private final List<Amenity> amenities;
	private final double price;
	private final Type type;

	protected Apartment(String name, double price, Type type, List<Amenity> amenities) {
		this.name = name;
		this.amenities = amenities;
		this.price = price;
		this.type = type;
	}

	public abstract void printInfo();

	// Getters
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Type getType() {
		return type;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}
}
