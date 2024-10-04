package org.example.lab_1;

public class Task5 {

	private static final String TARGET = "substring";
	private static final String[][] MATRIX = { // expected 2 true values
			{"Dota substring", "Valorant", "CSGO"},
			{"The witcher", "The Elder Scrolls substring", "Substring Sekiro"},
			{"Hearts of iron", "Civilization", "Europa sub string "}
	};


	public static void main(final String[] args) {
		System.out.println("Number of occurrences: " + countSubstring());
	}

	private static int countSubstring() {
		int count = 0;
		for (final String[] row : MATRIX) {
			for (final String col : row) {
				if (col.contains(TARGET)) {
					count++;
				}
			}
		}
		return count;
	}
}