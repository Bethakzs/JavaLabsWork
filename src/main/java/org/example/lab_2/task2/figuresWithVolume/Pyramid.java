package org.example.lab_2.task2.figuresWithVolume;

import org.example.lab_2.task2.ShapeWithVolume;

public class Pyramid extends ShapeWithVolume {
    private final double bottomLength;
    private final double bottomWidth;
    private final double height;

    public Pyramid(double bottomLength, double bottomWidth, double height) {
        if (bottomLength <= 0 || bottomWidth <= 0 || height <= 0) {
            throw new IllegalArgumentException("Sides of a pyramid must be greater than 0");
        }
        this.bottomLength = bottomLength;
        this.bottomWidth = bottomWidth;
        this.height = height;
    }

    @Override
    public double getVolume() {
        double baseArea = bottomLength * bottomWidth;
        return Math.round((1.0 / 3) * baseArea * height * 10.0) / 10.0;
    }

    @Override
    public double getArea() {
        double bottomArea = bottomLength * bottomWidth; // area of the bottom of the pyramid
        double sideHeight = Math.sqrt(Math.pow(height, 2) + Math.pow(bottomLength / 2, 2)); // height of the side of the pyramid
        double sideArea = bottomLength * sideHeight / 2; // area of the side of the pyramid
        double totalSideArea = 4 * sideArea; // 4 sides of the pyramid
        return Math.round(bottomArea + totalSideArea * 10.0) / 10.0;
    }
}