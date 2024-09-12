package org.example.lab_2.task2.figuresWithArea;

import org.example.lab_2.task2.ShapeWithArea;

public class Triangle extends ShapeWithArea {

    private final double bottom;
    private final double height;

    public Triangle(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be greater than 0");
        }
        this.bottom = base;
        this.height = height;
    }

    @Override
    public double getArea() {
        return 0.5 * bottom * height;
    }
}
