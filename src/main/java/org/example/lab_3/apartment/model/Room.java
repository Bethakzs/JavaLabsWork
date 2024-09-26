package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.List;

public class Room extends ApartmentCapacity {

    public Room(String name, Type type, int maxSpace, double price, List<Amenity> amenities) {
        super(name, maxSpace, price, type, amenities);
    }

    @Override
    public void printInfo() {
        System.out.println("Room name: " + super.getName());
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
}
