package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public class House extends Apartment {

    public House(String name, Type type, int maxSpace, double price, List<Amenity> amenities) {
        super(name, maxSpace, price, type, amenities);
    }

    @Override
    public void printInfo() {
        System.out.println("House name: " + super.getName());
        System.out.println("House type: " + getType());
        System.out.println("House max space for adults: " + super.getMaxSpace());
        System.out.println("House max space for children: " + super.getChildrenMaxSpace());
        System.out.println("House max space for animals: " + super.getAnimalMaxSpace());

        System.out.println("House amenities: ");
        super.getAmenities().stream()
                .map(Amenity::getAmenityType)
                .forEach(System.out::println);
        System.out.println();
    }
}