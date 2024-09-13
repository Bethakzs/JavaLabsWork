package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Cube extends ShapeWithVolume {

    private final double side;

    public Cube(final double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side of a cube must be greater than 0");
        }
        this.side = side;
    }

    @Override
    public double getVolume() {
        return Math.pow(side, 3);
    }

    @Override
    public double getArea() { // full area of a cube
        return 6 * Math.pow(side, 2);
    }
}