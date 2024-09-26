package org.example.lab_3.apartment.model;

import org.example.lab_3.amenity.model.Amenity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hotel extends Apartment {
    private final List<Room> rooms;

    public Hotel(String name, double price, Type type, List<Amenity> amenities, List<Room> rooms) {
        super(name, 0, price, type, amenities);
        this.rooms = rooms;
    }

    @Override
    public void printInfo() {
        System.out.println("Hotel name: " + getName());

        rooms.forEach(room -> {
            super.setMaxSpace(super.getMaxSpace() + room.getMaxSpace());
            this.setChildrenMaxSpace(super.getChildrenMaxSpace() + room.getChildrenMaxSpace());
            this.setAnimalMaxSpace(super.getAnimalMaxSpace() + room.getAnimalMaxSpace());
        });

        System.out.println("House max space for adults: " + super.getMaxSpace());
        System.out.println("House max space for children: " + super.getChildrenMaxSpace());
        System.out.println("House max space for animals: " + super.getAnimalMaxSpace());

        System.out.println("Hotel amenities: ");
        getAmenities().stream()
                .map(Amenity::getAmenityType)
                .forEach(System.out::println);
    }

    public void printRoomInfo(Room room) {
        System.out.println("Room name: " + room.getName());
        System.out.println("Room max space for adults: " + room.getMaxSpace());
        System.out.println("Room max space for children: " + room.getChildrenMaxSpace());
        System.out.println("Room max space for animals: " + room.getAnimalMaxSpace());

        Set<Amenity> uniqueAmenities = new HashSet<>(room.getAmenities());
        uniqueAmenities.addAll(getAmenities());

        if (uniqueAmenities.isEmpty()) {
            System.out.println("No amenities available for this room and hotel.\n");
        } else {
            System.out.println("Combined amenities: ");
            uniqueAmenities.stream()
                    .map(Amenity::getAmenityType)
                    .forEach(System.out::println);
            System.out.println();
        }
    }
}
