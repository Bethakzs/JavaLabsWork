package org.example.service;

import org.example.dto.request.JwtRequest;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.JwtResponse;
import org.example.entity.Role;

public interface AuthService {

	JwtResponse createAuthToken(JwtRequest authRequest);

	JwtResponse createNewUser(UserRegistration registrationDTO, Role role);

	JwtResponse refreshAuthToken(String refreshToken);

	void logoutUser(String refreshToken);

}
