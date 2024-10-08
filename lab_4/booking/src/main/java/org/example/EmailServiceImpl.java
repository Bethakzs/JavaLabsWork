package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	public void send(String to, String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailMessage.setFrom(from);

		mailSender.send(mailMessage);
	}

}
