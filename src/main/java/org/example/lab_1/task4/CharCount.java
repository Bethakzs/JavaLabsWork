package org.example.lab_1.task4;

public class CharCount {
	private final char[] characters;
	private final int[] counts;
	private int uniqueCharCount;

	public CharCount(int length) {
		characters = new char[length];
		counts = new int[length];
		uniqueCharCount = 0;
	}

	public void addCharacter(final char character) {
		for (int i = 0; i < uniqueCharCount; i++) {
			if (characters[i] == character) {
				counts[i]++;
				return;
			}
		}

		characters[uniqueCharCount] = character;
		counts[uniqueCharCount] = 1;
		uniqueCharCount++;
	}

	public void printAllCharacters() {
		for (int i = 0; i < uniqueCharCount; i++) {
			System.out.print(characters[i] + " = " + counts[i] + "; ");
		}
		System.out.println();
	}
}