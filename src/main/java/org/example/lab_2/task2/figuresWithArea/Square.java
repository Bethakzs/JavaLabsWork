package org.example.lab_2.task2.figuresWithArea;

import org.example.lab_2.task2.ShapeWithArea;

public class Square extends ShapeWithArea {

    private final double side;

    public Square(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side of a square must be greater than 0");
        }
        this.side = side;
    }

    @Override
    public double getArea() {
        return Math.pow(side, 2);
    }
}