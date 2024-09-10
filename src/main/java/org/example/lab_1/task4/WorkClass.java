package org.example.lab_1.task4;

import java.util.Scanner;

public class WorkClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string : ");
        String inputString = scanner.nextLine();
        countEveryCharIncluded(inputString);
        scanner.close();
    }

    public static void countEveryCharIncluded(final String row) {
        CharCount.reset();
        CharCount.initializeArray(row.length());
        final char[] chars = row.toCharArray();
        for (final char c : chars) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            CharCount.addCharacter(c);
        }
        CharCount.printAllCharacters();
    }
}
