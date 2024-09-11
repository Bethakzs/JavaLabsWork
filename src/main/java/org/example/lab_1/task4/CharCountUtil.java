package org.example.lab_1.task4;

public class CharCountUtil {

    public static void countEveryCharIncluded(final String row) {
        CharCount charCount = new CharCount(row.length());
        final char[] chars = row.toCharArray();
        for (final char c : chars) {
            charCount.addCharacter(c);
        }
        charCount.printAllCharacters();
    }
}