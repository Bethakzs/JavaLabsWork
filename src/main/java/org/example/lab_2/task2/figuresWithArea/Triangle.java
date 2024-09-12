package org.example.lab_2.task2.figuresWithArea;

import org.example.lab_2.task2.ShapeWithArea;

public class Triangle extends ShapeWithArea {

    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Sides of a triangle must be greater than 0");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Invalid sides of a triangle");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getArea() {  // Heron's formula
        double halfOfPerimeter = (a + b + c) / 2;
        return Math.sqrt(halfOfPerimeter * (halfOfPerimeter - a) * (halfOfPerimeter - b) * (halfOfPerimeter - c));
    }
}