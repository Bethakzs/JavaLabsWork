package org.example.lab_2.task2;

import java.util.Scanner;
import org.example.lab_2.task2.figuresWithArea.Circle;
import org.example.lab_2.task2.figuresWithArea.Rectangle;
import org.example.lab_2.task2.figuresWithArea.Square;
import org.example.lab_2.task2.figuresWithArea.Triangle;
import org.example.lab_2.task2.figuresWithVolume.Cube;
import org.example.lab_2.task2.figuresWithVolume.Pyramid;
import org.example.lab_2.task2.figuresWithVolume.Sphere;

public class WorkClass {
    public static void main(final String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter what you want to do: 1 - use test data, 2 - enter your data");
            final int choice = scanner.nextInt();

            if (choice == 1) {
                displayTestData();
            } else if (choice == 2) {
                displayUserData(scanner);
            } else {
                System.err.println("Invalid choice");
            }
        } catch (final Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void displayUserData(final Scanner scanner) {
        System.out.println("Enter the side length of the square:");
        final double squareSide = scanner.nextDouble();
        final Shape square = new Square(squareSide);

        System.out.println("Enter the width and height of the rectangle:");
        final double rectangleWidth = scanner.nextDouble();
        final double rectangleHeight = scanner.nextDouble();
        final Shape rectangle = new Rectangle(rectangleWidth, rectangleHeight);

        System.out.println("Enter the radius of the circle:");
        final double circleRadius = scanner.nextDouble();
        final Shape circle = new Circle(circleRadius);

        System.out.println("Enter the base and height of the triangle:");
        final double triangleBase = scanner.nextDouble();
        final double triangleHeight = scanner.nextDouble();
        final Shape triangle = new Triangle(triangleBase, triangleHeight);

        System.out.println("--------------------------------");

        System.out.println("Enter the side length of the cube:");
        final double cubeSide = scanner.nextDouble();
        final ShapeWithVolume cube = new Cube(cubeSide);

        System.out.println("Enter the radius of the sphere:");
        final double sphereRadius = scanner.nextDouble();
        final ShapeWithVolume sphere = new Sphere(sphereRadius);

        System.out.println("Enter the base, height, and slant height of the pyramid:");
        final double pyramidBase = scanner.nextDouble();
        final double pyramidHeight = scanner.nextDouble();
        final double pyramidSlantHeight = scanner.nextDouble();
        final ShapeWithVolume pyramid = new Pyramid(pyramidBase, pyramidHeight, pyramidSlantHeight);

        displayShapeData(square, rectangle, circle, triangle, cube, sphere, pyramid);
    }

    private static void displayTestData() {
        final Shape square = new Square(5);
        final Shape rectangle = new Rectangle(3, 4);
        final Shape circle = new Circle(3);
        final Shape triangle = new Triangle(6, 4);

        final ShapeWithVolume cube = new Cube(7);
        final ShapeWithVolume sphere = new Sphere(4);
        final ShapeWithVolume pyramid = new Pyramid(5, 4, 5);

        displayShapeData(square, rectangle, circle, triangle, cube, sphere, pyramid);
    }

    private static void displayShapeData(final Shape square, final Shape rectangle, final Shape circle, final Shape triangle,
                                         final ShapeWithVolume cube, final ShapeWithVolume sphere, final ShapeWithVolume pyramid) {
        System.out.println(square.getArea() + " - the area of the square");
        System.out.println(rectangle.getArea() + " - the area of the rectangle");
        System.out.println(circle.getArea() + " - the area of the circle");
        System.out.println(triangle.getArea() + " - the area of the triangle");

        System.out.println("--------------------------------");

        System.out.println(cube.getArea() + " - the area of the cube");
        System.out.println(cube.getVolume() + " - the volume of the cube");
        System.out.println(sphere.getArea() + " - the area of the sphere");
        System.out.println(sphere.getVolume() + " - the volume of the sphere");
        System.out.println(pyramid.getArea() + " - the area of the pyramid");
        System.out.println(pyramid.getVolume() + " - the volume of the pyramid");
    }
}