package org.example.error;

public class ApartmentUnavailableForDateRangeException extends RuntimeException {

	public ApartmentUnavailableForDateRangeException(String message) {
		super(message);
	}
}
