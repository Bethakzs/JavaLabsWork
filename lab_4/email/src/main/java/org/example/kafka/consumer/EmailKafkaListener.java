package org.example.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserDTO;
import org.example.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailKafkaListener {

	private final ObjectMapper objectMapper;
	private final EmailService emailService;

	@KafkaListener(topics = "email-registration-topic", groupId = "email-group")
	public void listenToUserRegistration(@Payload String userJson) {
		log.info("Received user registration message: {}", userJson);
		try {
			UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
			emailService.send(userDTO);
		} catch (JsonProcessingException e) {
			log.error("Error while processing user registration message: {}", e.getMessage());
		}
	}
}
