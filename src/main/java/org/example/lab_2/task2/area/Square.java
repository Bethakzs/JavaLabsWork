package org.example.lab_2.task2.area;

import org.example.lab_2.task2.Shape;

public class Square extends Shape {

	private final double side;

	public Square(final double side) {
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