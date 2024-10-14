package org.example.error;

public class ApartmentAlreadyBookedException extends RuntimeException {

	public ApartmentAlreadyBookedException(String message) {
		super(message);
	}
}
