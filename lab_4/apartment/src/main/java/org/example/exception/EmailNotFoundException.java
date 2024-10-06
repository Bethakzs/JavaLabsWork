package org.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailNotFoundException extends RuntimeException {
	private int status;
	private String message;
	private Date timestamp;

	public EmailNotFoundException(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
