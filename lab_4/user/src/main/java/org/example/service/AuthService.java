package org.example.service;

import org.example.dto.request.JwtRequest;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.JwtResponse;

public interface AuthService {

	JwtResponse createAuthToken(JwtRequest authRequest);

	JwtResponse createNewUser(UserRegistration registrationDTO);

	JwtResponse refreshAuthToken(String refreshToken);

	void logoutUser(String refreshToken);
}
