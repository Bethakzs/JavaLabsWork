package org.example.task3;

import java.util.Scanner;

public class WorkClass {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter what you want to do: 1 - upload test data, 2 - enter your data");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1 -> uploadTestData();
				case 2 -> uploadUserData(scanner);
				default -> System.err.println("Invalid choice");
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println("Unexpected error " + e.getMessage());
		}
	}

	private static void uploadUserData(Scanner scanner) {
		final Warehouse warehouse = new Warehouse();

		System.out.println("Enter the number of boxes :");
		int boxCount = scanner.nextInt();
		if (boxCount <= 0) {
			throw new IllegalArgumentException("The number of boxes must be greater than 0");
		}

		for (int i = 0; i < boxCount; i++) {
			System.out.println("Enter the length of box " + (i + 1) + " :");
			double length = scanner.nextDouble();
			System.out.println("Enter the width of box " + (i + 1) + " :");
			double width = scanner.nextDouble();
			System.out.println("Enter the height of box " + (i + 1) + " :");
			double height = scanner.nextDouble();

			final Box box = new Box(length, width, height);
			warehouse.addBox(box);

			System.out.println("Enter the number of goods for box " + (i + 1) + " :");
			int goodCount = scanner.nextInt();
			if (goodCount <= 0) {
				throw new IllegalArgumentException("The number of goods must be greater than 0");
			}

			for (int j = 0; j < goodCount; j++) {
				System.out.println("Enter the length of good " + (j + 1) + " :");
				double goodLength = scanner.nextDouble();
				System.out.println("Enter the width of good " + (j + 1) + " :");
				double goodWidth = scanner.nextDouble();
				System.out.println("Enter the height of good " + (j + 1) + " :");
				double goodHeight = scanner.nextDouble();

				final Good good = new Good(goodLength, goodWidth, goodHeight);
				try {
					warehouse.addGoodToBox(box, good);
					System.out.println("Good was added to the box");
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		warehouse.printAllInfo();
	}

	private static void uploadTestData() {
		final Warehouse warehouse = new Warehouse();

		final Box box1 = new Box(5, 5, 5);
		final Box box2 = new Box(10, 10, 10);

		final Good good1 = new Good(1, 1, 1);
		final Good good2 = new Good(2, 2, 2);
		final Good good3 = new Good(3, 3, 3);
		final Good good4 = new Good(4, 4, 4);
		final Good good5 = new Good(5, 5, 5);
		final Good good6 = new Good(6, 6, 6);

		warehouse.addBox(box1);
		warehouse.addBox(box2);

		warehouse.addGoodToBox(box1, good1);
		warehouse.addGoodToBox(box1, good2);
		warehouse.addGoodToBox(box1, good3);
		warehouse.addGoodToBox(box1, good4);
		warehouse.addGoodToBox(box1, good5);
		try {
			warehouse.addGoodToBox(box1, good6);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}

		warehouse.printAllInfo();
	}
}