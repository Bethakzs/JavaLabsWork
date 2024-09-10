package org.example.lab_1;

import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {
        System.out.println("Enter the example in the format: num1 operator num2");
        try (final Scanner scanner = new Scanner(System.in)) {
            String example = scanner.nextLine();
            example = example.trim().replace(" =", "").replace("?", "").trim();

            final double result = calculate(example);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static double calculate(final String example) {
        final String[] parts = example.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid example format");
        }

        final double num1 = Double.parseDouble(parts[0]);
        final double num2 = Double.parseDouble(parts[2]);
        final String operator = parts[1];

        return performOperation(num1, num2, operator);
    }

    private static double performOperation(final double num1, final double num2, final String operator) {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Unknown operator "
                    + operator + " choose from +, -, *, /");
        };
    }
}
