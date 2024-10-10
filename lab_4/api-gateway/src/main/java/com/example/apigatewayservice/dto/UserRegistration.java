package com.example.apigatewayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
}
