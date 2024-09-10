package org.example.lab_1;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input value : ");
            final int number = scanner.nextInt();
            final String binaryRepresentation = Integer.toBinaryString(number);
            final String formattedBinary = String.format("%8s", binaryRepresentation).replace(' ', '0');
            System.out.println("Binary value : " + formattedBinary);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
