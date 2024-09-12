package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Pyramid extends ShapeWithVolume {

    private final double baseArea;
    private final double height;

    public Pyramid(double baseArea, double height) {
        if (baseArea <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base area and height must be greater than 0");
        }
        this.baseArea = baseArea;
        this.height = height;
    }

    @Override
    public double getVolume() {
        return 1.0 / 3.0 * baseArea * height;
    }
}