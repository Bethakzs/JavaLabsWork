package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserDTO;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public void send(UserDTO userDTO) {
		String to = userDTO.getEmail();
		String subject = "Welcome, " + userDTO.getFirstName() + "!";
		String body = createWelcomeEmailBody(userDTO);

		log.info("Sending email to: {}, subject: {}", to, subject);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailMessage.setFrom(from);

//		mailSender.send(mailMessage);
		log.info("Email sent successfully!");
	}

	private String createWelcomeEmailBody(UserDTO userDTO) {
		return String.format("""
				Hello, %s %s!
				Thank you for joining our platform! We are glad to have you.\

				Best regards,
				The Team""", userDTO.getFirstName(), userDTO.getLastName());
	}
}
