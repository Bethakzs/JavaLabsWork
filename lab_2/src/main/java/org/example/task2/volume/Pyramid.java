package org.example.task2.volume;

import org.example.task2.ShapeWithVolume;

public class Pyramid extends ShapeWithVolume {
	private final double bottomLength;
	private final double bottomWidth;
	private final double height;

	public Pyramid(final double bottomLength, final double bottomWidth, final double height) {
		if (bottomLength <= 0 || bottomWidth <= 0 || height <= 0) {
			throw new IllegalArgumentException("Sides of a pyramid must be greater than 0");
		}
		this.bottomLength = bottomLength;
		this.bottomWidth = bottomWidth;
		this.height = height;
	}

	@Override
	public double getVolume() {
		final double baseArea = bottomLength * bottomWidth;
		return Math.round((1.0 / 3) * baseArea * height * 10.0) / 10.0;
	}

	@Override
	public double getArea() {
		final double bottomArea = bottomLength * bottomWidth; // area of the bottom of the pyramid
		final double sideHeight = Math.sqrt(Math.pow(height, 2) + Math.pow(bottomLength / 2, 2)); // height of the side of the pyramid
		final double sideArea = bottomLength * sideHeight / 2; // area of the side of the pyramid
		final double totalSideArea = 4 * sideArea; // 4 sides of the pyramid
		return Math.round(bottomArea + totalSideArea * 10.0) / 10.0;
	}
}