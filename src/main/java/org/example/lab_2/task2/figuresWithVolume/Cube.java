package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Cube extends ShapeWithVolume {

    private final double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double getVolume() {
        return Math.pow(side, 3);
    }
}