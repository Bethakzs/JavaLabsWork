package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public class Room extends Apartment {
    private final int number;

    public Room(String name, Type type, int maxSpace, int number,  double price, List<Amenity> amenities) {
        super(name, maxSpace, price, type, amenities);
        this.number = number;
    }

    @Override
    public void printInfo() {
        System.out.println("Room name: " + super.getName());
        System.out.println("Room number: " + getNumber());
        System.out.println("Room type: " + getType());
        System.out.println("Room max space for adults: " + super.getMaxSpace());
        System.out.println("Room max space for children: " + super.getChildrenMaxSpace());
        System.out.println("Room max space for animals: " + super.getAnimalMaxSpace());

        System.out.println("Room amenities: ");
        super.getAmenities().stream()
                .map(Amenity::getAmenityType)
                .forEach(System.out::println);
        System.out.println();
    }

    // Getter
    public int getNumber() {
        return number;
    }
}
