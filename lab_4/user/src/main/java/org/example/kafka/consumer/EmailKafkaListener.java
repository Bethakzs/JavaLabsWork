package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailKafkaListener {

	private final UserService userService;
	private final KafkaTemplate<String, UserDTO> kafkaTemplate;

	@KafkaListener(topics = "get-user-by-email-topic", groupId = "user-group")
	public void listenToUserRequest(@Payload String email) {
		log.info("Received user email message: {}", email);
		try {
			User user = userService.findByEmail(email);

			UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getBalance());
			kafkaTemplate.send("return-booking-user-topic", userDTO);
			log.info("Sent UserDTO to Kafka: {}", userDTO);
		} catch (NumberFormatException | UserNotFoundException e) {
			log.error("Error while processing user id message: {}", e.getMessage());
		}
	}

	@KafkaListener(topics = "user-withdraw-balance-topic", groupId = "balance-group")
	public void listenToBalanceWithdrawal(@Payload String message) {
		log.info("Received balance withdrawal request: {}", message);
		try {
			String[] parts = message.split(",");
			String email = parts[0];
			BigDecimal amount = new BigDecimal(parts[1]);

			userService.withdrawBalance(email, amount);
		} catch (Exception e) {
			log.error("Error processing balance withdrawal message: {}", e.getMessage());
		}
	}
}

