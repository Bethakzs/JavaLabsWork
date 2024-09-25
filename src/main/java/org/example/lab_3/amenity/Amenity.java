package org.example.lab_3.amenity;

public class Amenity {
    private AmenityType amenityType;
    private int additionalSpace;
    private Category category;

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
