package org.example.task3;

import java.util.ArrayList;

public class Warehouse {

	private final ArrayList<Box> boxes = new ArrayList<>();

	public void addBox(final Box box) {
		boxes.add(box);
	}

	public void addGoodToBox(final Box box, final Good good) {
		if (good.height() > box.getHeight() || good.width() > box.getWidth() || good.length() > box.getLength()) {
			throw new IllegalArgumentException("Good is too large for the box");
		}
		box.addGood(good);
	}

	public void printAllInfo() {
		for (final Box box : boxes) {
			if (box.getGoods().isEmpty()) {
				System.out.println("Box is empty");
			} else {
				System.out.println("Box : ");
				for (final Good good : box.getGoods()) {
					System.out.println(good);
				}
			}
		}
	}
}