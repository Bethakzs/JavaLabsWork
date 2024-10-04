package org.example.lab_1;

public class Task3 {
	public static void main(String[] args) {
		fizzBuzz();
	}

	private static void fizzBuzz() {
		final StringBuilder result = new StringBuilder();
		for (int i = 1; i <= 100; i++) {
			if (i % 3 == 0) result.append("Fizz");
			if (i % 5 == 0) result.append("Buzz");

			if (!result.isEmpty()) {
				System.out.println(result);
				result.setLength(0);
				continue;
			}
			System.out.println(i + " ");
		}
	}
}
