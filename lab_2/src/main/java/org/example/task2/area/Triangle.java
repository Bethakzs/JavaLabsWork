package org.example.task2.area;

import org.example.task2.Shape;

public class Triangle extends Shape {

	private final double bottom;
	private final double height;

	public Triangle(final double base, final double height) {
		if (base <= 0 || height <= 0) {
			throw new IllegalArgumentException("Base and height must be greater than 0");
		}
		this.bottom = base;
		this.height = height;
	}

	@Override
	public double getArea() {
		return 0.5 * bottom * height;
	}
}
