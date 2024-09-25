package org.example.lab_3;

import org.example.lab_3.amenity.Amenity;
import org.example.lab_3.amenity.AmenityType;
import org.example.lab_3.apartment.*;

import java.util.ArrayList;
import java.util.List;

import static org.example.lab_3.amenity.Category.*;

public class WorkClass {

    public static void main(String[] args) {
        TestResources.house3.printInfo();
        System.out.println("--------------------------------------------------");
        TestResources.room2.printInfo();
        System.out.println("--------------------------------------------------");
        TestResources.hotel1.printInfo(TestResources.room2);
        System.out.println("--------------------------------------------------");
    }

    static class TestResources {
        private final static Amenity amenity1 = new Amenity(AmenityType.WI_FI);
        private final static Amenity amenity2 = new Amenity(AmenityType.KITCHEN);
        private final static Amenity amenity3 = new Amenity(AmenityType.BATHROOM);
        private final static Amenity amenity4 = new Amenity(AmenityType.SECOND_BATHROOM);
        private final static Amenity amenity5 = new Amenity(AmenityType.SPA);
        private final static Amenity amenity6 = new Amenity(AmenityType.FOOTBALL_STADIUM);
        private final static Amenity amenity7 = new Amenity(AmenityType.SWIMMING_POOL);
        private final static Amenity amenity8 = new Amenity(AmenityType.SOFA, 1, ADULT);
        private final static Amenity amenity9 = new Amenity(AmenityType.DOUBLE_SOFA, 2, ADULT);
        private final static Amenity amenity10 = new Amenity(AmenityType.CHILD_BED, 1, CHILD);
        private final static Amenity amenity11 = new Amenity(AmenityType.DOG_HOUSE, 1, ANIMAL);

        private final static Apartment house1 = new House("House 1", Type.ECONOMIC, 3,
                new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity9)));
        private final static Apartment house2 = new House("House 2", Type.STANDARD, 6,
                new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity5)));
        private final static Apartment house3 = new House("House 3", Type.LUXURY, 10,
                new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity4, amenity5, amenity6, amenity7, amenity10)));
        private final static Apartment house4 = new House("House 4", Type.APARTMENT, 3,
                new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity4, amenity5, amenity6, amenity7, amenity11)));

        private final static Room room1 = new Room("Room 1", Type.APARTMENT, 3, 101,
                new ArrayList<>(List.of(amenity1, amenity2, amenity10)));
        private final static Room room2 = new Room("Room 2", Type.APARTMENT, 4, 102,
                new ArrayList<>(List.of(amenity1, amenity4, amenity9)));
        private final static Room room3 = new Room("Room 3", Type.APARTMENT, 3, 103,
                new ArrayList<>());

        private final static Hotel hotel1 = new Hotel("Hotel 1",
                new ArrayList<>(List.of(amenity1, amenity2, amenity3, amenity11)),
                new ArrayList<>(List.of(room1, room2, room3)));
    }
}
