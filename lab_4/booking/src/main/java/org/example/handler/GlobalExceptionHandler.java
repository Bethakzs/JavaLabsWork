package org.example.handler;

import org.example.entity.booking.exception.ApartmentNotFoundException;
import org.example.entity.booking.exception.ApartmentUnavailableForDateRangeException;
import org.example.entity.booking.exception.KafkaTimeoutException;
import org.example.entity.booking.exception.UserNotFoundException;
import org.example.exception.AmenityNotExistException;
import org.example.exception.HotelNotFoundException;
import org.example.exception.HouseNotFoundException;
import org.example.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AmenityNotExistException.class)
	public ResponseEntity<String> handleAmenityNotExist(AmenityNotExistException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(HouseNotFoundException.class)
	public ResponseEntity<String> handleHouseNotFound(HouseNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(HotelNotFoundException.class)
	public ResponseEntity<String> handleHotelNotFound(HotelNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(RoomNotFoundException.class)
	public ResponseEntity<String> handleRoomNotFound(RoomNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(ApartmentUnavailableForDateRangeException.class)
	public ResponseEntity<String> handleApartmentUnavailable(ApartmentUnavailableForDateRangeException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(ApartmentNotFoundException.class)
	public ResponseEntity<String> handleApartmentNotFound(ApartmentNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(KafkaTimeoutException.class)
	public ResponseEntity<String> handleKafkaTimeout(KafkaTimeoutException e) {
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
