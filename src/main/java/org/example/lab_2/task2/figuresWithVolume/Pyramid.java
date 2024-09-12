package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Pyramid extends ShapeWithVolume {

    private final double bottomBottom;
    private final double bottomHeight;
    private final double height;

    public Pyramid(double bottomBottom, double bottomHeight, double height) {
        if (bottomBottom <= 0 || bottomHeight <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base dimensions and height must be greater than 0");
        }
        this.bottomBottom = bottomBottom;
        this.bottomHeight = bottomHeight;
        this.height = height;
    }

    @Override
    public double getVolume() {
        return Math.round((1.0 / 3.0) * getArea() * height * 10.0) / 10.0;
    }

    @Override
    public double getArea() { // area of a bottom pyramid
        return 0.5 * bottomBottom * bottomHeight;
    }
}