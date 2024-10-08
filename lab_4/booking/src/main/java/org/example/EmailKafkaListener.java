package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailKafkaListener {

	private final EmailServiceImpl emailService;

	@KafkaListener(topics = "${kafka.topic.email}", groupId = "${kafka.group.id}")
	public void listenToEmailRequests(@Payload EmailRequest emailRequest) {
		System.out.println("Received email request: " + emailRequest);
		emailService.send(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
	}
}
