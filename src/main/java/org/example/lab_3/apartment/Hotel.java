package org.example.lab_3.apartment;

import org.example.lab_3.amenity.Amenity;

import java.util.List;

public class Hotel extends Apartment {
    private List<Room> rooms;

    public Hotel(String name, List<Amenity> amenities, List<Room> rooms) {
        super(name, 0, amenities);
        this.rooms = rooms;
    }

    @Override
    public void printInfo() {
        System.out.println("Hotel name: " + getName());

        for(Room room : rooms) {
            super.setMaxSpace(super.getMaxSpace() + room.getMaxSpace());
            this.setChildrenMaxSpace(super.getChildrenMaxSpace() + room.getChildrenMaxSpace());
            this.setAnimalMaxSpace(super.getAnimalMaxSpace() + room.getAnimalMaxSpace());
        }
        System.out.println("House max space for adults: " + super.getMaxSpace());
        System.out.println("House max space for children: " + super.getChildrenMaxSpace());
        System.out.println("House max space for animals: " + super.getAnimalMaxSpace());

        System.out.println("Hotel amenities: ");
        for (Amenity amenity : getAmenities()) {
            System.out.println(amenity.getAmenityType());
        }

        System.out.println("Hotel rooms: ");
        for (Room room : getRooms()) {
            System.out.print(room.getName());
            if(room.getAmenities().isEmpty()) {
                System.out.println("\n");
                continue;
            }

            System.out.println(" amenities: ");
            for(Amenity amenity : room.getAmenities()) {
                System.out.println(amenity.getAmenityType());
            }
            System.out.println();
        }
    }

    public void printInfo(Room room2) {
        printInfo();
        room2.printInfo();
    }

    // Getter
    public List<Room> getRooms() {
        return this.rooms;
    }
}
