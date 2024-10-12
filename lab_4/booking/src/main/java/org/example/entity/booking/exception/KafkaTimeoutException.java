package org.example.entity.booking.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class KafkaTimeoutException extends RuntimeException {
	private final int status;
	private final String message;
	private final Date timestamp;

	public KafkaTimeoutException(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = new Date();
	}
}
