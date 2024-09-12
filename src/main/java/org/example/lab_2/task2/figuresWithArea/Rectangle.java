package org.example.lab_2.task2.figuresWithArea;

import org.example.lab_2.task2.ShapeWithArea;

public class Rectangle extends ShapeWithArea {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Sides of a rectangle must be greater than 0");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}