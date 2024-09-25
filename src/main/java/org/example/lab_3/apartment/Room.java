package org.example.lab_3.apartment;

import org.example.lab_3.amenity.Amenity;

import java.util.List;

public class Room extends Apartment{
    private final int number;
    private final Type type;

    public Room(String name, Type type, int maxSpace, int number, List<Amenity> amenities) {
        super(name, maxSpace, amenities);
        this.type = type;
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
        for (Amenity amenity : super.getAmenities()) {
            System.out.println(amenity.getAmenityType());
        }
        System.out.println();
    }

    // Getters
    public int getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }
}
