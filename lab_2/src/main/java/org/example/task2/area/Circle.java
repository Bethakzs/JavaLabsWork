package org.example.task2.area;

import org.example.task2.Shape;

public class Circle extends Shape {

	private final double radius;

	public Circle(final double radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("Radius must be greater than 0");
		}
		this.radius = radius;
	}

	@Override
	public double getArea() {
		return Math.round(Math.PI * Math.pow(radius, 2) * 10.0) / 10.0;
	}
}