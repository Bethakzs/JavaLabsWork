package org.example.lab_2.task2;

import org.example.lab_2.task2.figuresWithArea.Circle;
import org.example.lab_2.task2.figuresWithArea.Rectangle;
import org.example.lab_2.task2.figuresWithArea.Square;
import org.example.lab_2.task2.figuresWithArea.Triangle;
import org.example.lab_2.task2.figuresWithVolume.Cube;
import org.example.lab_2.task2.figuresWithVolume.Pyramid;
import org.example.lab_2.task2.figuresWithVolume.Sphere;

public class WorkClass {
    public static void main(String[] args) {
        ShapeWithArea square = new Square(5);
        ShapeWithArea rectangle = new Rectangle(3, 4);
        ShapeWithArea circle = new Circle(3);
        ShapeWithArea triangle = new Triangle(6, 4);

        System.out.println(square.getArea() + " - the area of the square");
        System.out.println(rectangle.getArea() + " - the area of the rectangle");
        System.out.println(circle.getArea() + " - the area of the circle");
        System.out.println(triangle.getArea() + " - the area of the triangle");

        System.out.println("--------------------------------");

        ShapeWithVolume cube = new Cube(5);
        ShapeWithVolume sphere = new Sphere(3);
        ShapeWithVolume pyramid = new Pyramid(5, 5, 5);

        System.out.println(cube.getVolume() + " - the volume of the cube");
        System.out.println(sphere.getVolume() + " - the volume of the sphere");
        System.out.println(pyramid.getVolume() + " - the volume of the pyramid");
    }
}