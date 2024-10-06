package org.example.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmptyArgumentException extends RuntimeException {
	private int status;
	private String message;
	private Date timestamp;

	public EmptyArgumentException(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
