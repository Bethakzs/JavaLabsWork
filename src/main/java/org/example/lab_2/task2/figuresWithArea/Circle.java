package org.example.lab_2.task2.figuresWithArea;

import org.example.lab_2.task2.ShapeWithArea;

public class Circle extends ShapeWithArea {

    private final double radius;

    public Circle(double radius) {
        if(radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.round(Math.PI * Math.pow(radius, 2) * 10.0) / 10.0;
    }
}