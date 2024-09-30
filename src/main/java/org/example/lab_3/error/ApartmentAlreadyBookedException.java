package org.example.lab_3.error;

public class ApartmentAlreadyBookedException extends RuntimeException {

	public ApartmentAlreadyBookedException(String message) {
		super(message);
	}
}
