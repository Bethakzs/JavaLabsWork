package org.example.lab_1.task4;

public class CharCount {
    private final char character;
    private int count;
    private static CharCount[] allCharacters;
    private static int counter = 0;

    private CharCount(final char character) {
        this.character = character;
        this.count = 1;
    }

    public static void initializeArray(final int length) {
        allCharacters = new CharCount[length];
    }

    public static void addCharacter(final char character) {
        for (int i = 0; i < counter; i++) {
            if (allCharacters[i].character == character) {
                allCharacters[i].count++;
                return;
            }
        }
        if (counter < allCharacters.length) {
            allCharacters[counter++] = new CharCount(character);
        }
    }

    public static void printAllCharacters() {
        for (int i = 0; i < counter; i++) {
            System.out.print(allCharacters[i].character + " = " + allCharacters[i].count + "; ");
        }
        System.out.println();
    }

    public static void reset() {
        allCharacters = new CharCount[100]; // Assigning a fixed size instead of an empty array
        counter = 0;
    }
}