package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public abstract class Apartment {
    private final String name;
    private int maxSpace;
    private int childrenMaxSpace = 0;
    private int animalMaxSpace = 0;
    private final List<Amenity> amenities;
    private final double price;

    public Apartment(String name, int maxSpace, double price, List<Amenity> amenities) {
        this.name = name;
        this.maxSpace = maxSpace;
        this.amenities = amenities;
        this.price = price;

        amenities.forEach(amenity -> {
            if (amenity.getCategory() != null) {
                switch (amenity.getCategory()) {
                    case ADULT -> this.maxSpace += amenity.getAdditionalSpace();
                    case CHILD -> this.childrenMaxSpace += amenity.getAdditionalSpace();
                    case ANIMAL -> this.animalMaxSpace += amenity.getAdditionalSpace();
                    default -> throw new IllegalArgumentException("Incorrect category");
                }
            }
        });
    }

    public abstract void printInfo();

    // Getters
    public String getName() {
        return name;
    }

    public int getMaxSpace() {
        return maxSpace;
    }

    public int getChildrenMaxSpace() {
        return childrenMaxSpace;
    }

    public int getAnimalMaxSpace() {
        return animalMaxSpace;
    }

    public double getPrice() {
        return price;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setMaxSpace(int maxSpace) {
        this.maxSpace = maxSpace;
    }

    public void setChildrenMaxSpace(int childrenMaxSpace) {
        this.childrenMaxSpace = childrenMaxSpace;
    }

    public void setAnimalMaxSpace(int animalMaxSpace) {
        this.animalMaxSpace = animalMaxSpace;
    }
}
