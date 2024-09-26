package org.example.lab_3.amenity.model;

public class Amenity {
    private final AmenityType amenityType;
    private final int additionalSpace;
    private final Category category;

    public Amenity(AmenityType amenityType) {
        this.amenityType = amenityType;
        this.additionalSpace = 0;
        this.category = null;
    }

    public Amenity(AmenityType amenityType, int additionalSpace, Category category) {
        this.amenityType = amenityType;
        this.additionalSpace = additionalSpace;
        this.category = category;
    }

    // Getters
    public AmenityType getAmenityType() {
        return amenityType;
    }

    public int getAdditionalSpace() {
        return additionalSpace;
    }

    public Category getCategory() {
        return category;
    }
}
