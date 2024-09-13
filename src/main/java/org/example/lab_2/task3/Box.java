package org.example.lab_2.task3;

import java.util.ArrayList;

import java.util.List;

public class Box {

    private final List<Good> goods;
    private final double length;
    private final double width;
    private final double height;

    public Box(double length, double width, double height) {
        if(length <= 0 || width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Length, width and height must be greater than 0");
        }
        this.goods = new ArrayList<>();
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public void addGood(final Good good) {
        if (goods.size() >= 5) {
            throw new IllegalArgumentException("Box is full");
        }
        if (good.width() > this.width || good.height() > this.height || good.length() > this.length) {
            throw new IllegalArgumentException("Good is too large for the box");
        }
        goods.add(good);
    }

    public List<Good> getGoods() {
        return goods;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}