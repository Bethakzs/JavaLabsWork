package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Sphere extends ShapeWithVolume {

    private final double radius;

    public Sphere(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius of a sphere must be greater than 0");
        }
        this.radius = radius;
    }

    @Override
    public double getVolume() {
        return Math.round(4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * 10.0) / 10.0;
    }

    @Override
    public double getArea() { // full area of a sphere
        return Math.round(4 * Math.PI * Math.pow(radius, 2) * 10.0) / 10.0;
    }
}