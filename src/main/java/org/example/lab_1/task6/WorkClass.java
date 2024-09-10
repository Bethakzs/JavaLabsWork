package org.example.lab_1.task6;

import java.util.Scanner;

public class WorkClass {

    public static void main(String[] args) {
        final CurrencyConverter converter = new CurrencyConverter();
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input your currency in format '100 UAH into USD': ");
            final String input = scanner.nextLine();

            final double result = converter.convertCurrency(input);
            System.out.println("Result of conversion: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}