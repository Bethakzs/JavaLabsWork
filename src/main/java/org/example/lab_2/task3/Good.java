package org.example.lab_2.task3;

public record Good(double length, double width, double height) {

    public Good {
        if (length <= 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Length, width and height must be greater than 0");
        }
    }

    @Override
    public String toString() {
        return "Good with parameters |" +
                " length=" + length +
                ", width=" + width +
                ", height=" + height +
                " |";
    }
}