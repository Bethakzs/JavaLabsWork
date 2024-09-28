package org.example.lab_3.consumer.model;

public record Consumer(String firstName, String lastName, String phoneNumber, String email) {

	@Override
	public String toString() {
		return "Consumer: " + firstName + " " + lastName + "    " +
				"Phone number: " + phoneNumber + "    " +
				"Email: " + email;
	}
}
