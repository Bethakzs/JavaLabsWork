package org.example.task2.area;

import org.example.task2.Shape;

public class Rectangle extends Shape {

	private final double width;
	private final double height;

	public Rectangle(final double width, final double height) {
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