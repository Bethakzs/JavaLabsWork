package org.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRefreshTokenException extends RuntimeException {
	private final int status;
	private final String message;
	private final Date timestamp;

	public BadRefreshTokenException(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
