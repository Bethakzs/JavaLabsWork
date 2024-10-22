package org.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.booking.UserDTO;
import org.example.exception.KafkaTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private CompletableFuture<UserDTO> userFuture = new CompletableFuture<>();

	@KafkaListener(topics = "return-booking-user-topic", groupId = "booking-group")
	public void consumeUserDTO(String stringUserDTO) {
		try {
			UserDTO userDTO = objectMapper.readValue(stringUserDTO, UserDTO.class);
			log.info("Received UserDTO from Kafka: {}", userDTO);
			userFuture.complete(userDTO);
		} catch (Exception e) {
			log.error("Error while parsing UserDTO from Kafka message: {}", e.getMessage());
			userFuture.completeExceptionally(e);
		}
	}

	public UserDTO getUserFromKafka(String email) {
		try {
			requestUserByEmail(email);
			return userFuture.get(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("Thread was interrupted while waiting for Kafka response: {}", e.getMessage());
			throw new KafkaTimeoutException(HttpStatus.REQUEST_TIMEOUT.value(), "Kafka response interrupted");
		} catch (ExecutionException e) {
			log.error("Error during execution while waiting for Kafka response: {}", e.getMessage());
			throw new KafkaTimeoutException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error during Kafka execution");
		} catch (TimeoutException e) {
			log.error("Timeout while waiting for Kafka response: {}", e.getMessage());
			throw new KafkaTimeoutException(HttpStatus.REQUEST_TIMEOUT.value(), "Kafka response timeout");
		} finally {
			userFuture = new CompletableFuture<>();
		}
	}


	private void requestUserByEmail(String email) {
		log.info("Requesting user with email: {}", email);
		kafkaTemplate.send("get-user-by-email-topic", email);
	}
}
