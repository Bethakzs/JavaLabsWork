package org.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends RuntimeException {
	private int status;
	private String message;
	private Date timestamp;

	public UserNotFoundException(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
