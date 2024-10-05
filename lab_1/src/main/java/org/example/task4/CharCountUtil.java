package org.example.task4;

public class CharCountUtil {

	private CharCountUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void countEveryCharIncluded(final String row) {
		CharCount charCount = new CharCount(row.length());
		final char[] chars = row.toCharArray();
		for (final char c : chars) {
			charCount.addCharacter(c);
		}
		charCount.printAllCharacters();
	}
}