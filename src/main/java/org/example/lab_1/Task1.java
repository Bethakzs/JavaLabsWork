package org.example.lab_1;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input value: ");
            final int number = scanner.nextInt();
            System.out.println("Binary value: " + convertToBinary(number));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String convertToBinary(int number) {
        if (number == 0) {
            return "0";
        }

        final StringBuilder binary = new StringBuilder();
        while (number > 0) {
            binary.insert(0, number % 2);
            number /= 2;
        }

        return String.format("%8s", binary).replace(' ', '0');
    }
}
