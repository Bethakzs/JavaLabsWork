package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.JwtRequest;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.JwtResponse;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.BadRefreshTokenException;
import org.example.exception.EmptyArgumentException;
import org.example.exception.UserAlreadyExistException;
import org.example.security.JwtTokenProvider;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final String INVALID_REFRESH_TOKEN = "Invalid refreshToken";
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	public JwtResponse createAuthToken(JwtRequest authRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

		User user = userService.findByEmail(authRequest.getEmail());
		return getJwtResponse(user);
	}

	@Transactional
	public JwtResponse createNewUser(UserRegistration registrationDTO, Role role) {
		if (!userService.isAvailableUser(registrationDTO)) {
			throw new UserAlreadyExistException(HttpStatus.CONFLICT.value(), "User with this credentials already exists");
		}

		User user = userService.createUser(registrationDTO, role);
		return getJwtResponse(user);
	}

	public JwtResponse refreshAuthToken(String refreshToken) {
		User user = userService.findByRefreshToken(refreshToken).orElseThrow(() ->
				new BadRefreshTokenException(HttpStatus.UNAUTHORIZED.value(), INVALID_REFRESH_TOKEN));
		if (!jwtTokenProvider.validateToken(refreshToken, user.getEmail())) {
			throw new BadRefreshTokenException(HttpStatus.UNAUTHORIZED.value(), INVALID_REFRESH_TOKEN);
		}

		return getJwtResponse(user);
	}

	@Transactional
	public void logoutUser(String refreshToken) {
		User user = userService.findByRefreshToken(refreshToken).orElseThrow(() ->
				new EmptyArgumentException(HttpStatus.BAD_REQUEST.value(), INVALID_REFRESH_TOKEN));
		user.setRefreshToken("");
		userService.updateUser(user);
	}

	private JwtResponse getJwtResponse(User user) {
		UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
		user.setRefreshToken(refreshToken);
		userService.updateUser(user);

		log.info("User {} logged in", user.getEmail());
		return new JwtResponse(accessToken, refreshToken, user.getRoles());
	}
}
