package org.example.lab_3.apartment;

import org.example.lab_3.amenity.Amenity;

import java.util.List;

public class House extends Apartment {

    private final Type type;

    public House(String name, Type type, int maxSpace, List<Amenity> amenities) {
        super(name, maxSpace, amenities);
        this.type = type;
    }

    @Override
    public void printInfo() {
        System.out.println("House name: " + super.getName());
        System.out.println("House type: " + getType());
        System.out.println("House max space for adults: " + super.getMaxSpace());
        System.out.println("House max space for children: " + super.getChildrenMaxSpace());
        System.out.println("House max space for animals: " + super.getAnimalMaxSpace());

        System.out.println("House amenities: ");
        for (Amenity amenity : super.getAmenities()) {
            System.out.println(amenity.getAmenityType());
        }
        System.out.println();
    }

    // Getter

    public Type getType() {
        return this.type;
    }
}

