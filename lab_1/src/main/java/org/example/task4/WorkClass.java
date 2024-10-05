package org.example.task4;

import java.util.Scanner;

public class WorkClass {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter the string: ");
			String inputString = scanner.nextLine();
			CharCountUtil.countEveryCharIncluded(inputString);
		}
	}
}