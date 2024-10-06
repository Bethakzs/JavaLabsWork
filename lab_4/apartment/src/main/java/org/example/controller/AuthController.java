package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.JwtRequest;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.JwtResponse;
import org.example.entity.Role;
import org.example.exception.BadRefreshTokenException;
import org.example.exception.EmptyArgumentException;
import org.example.exception.UserAlreadyExistException;
import org.example.service.AuthService;
import org.example.util.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest, HttpServletResponse response) {
		try {
			JwtResponse jwtResponse = authService.createAuthToken(authRequest);
			return getMapResponseEntity(response, jwtResponse);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/registration")
	public ResponseEntity<?> createNewUser(@RequestBody UserRegistration regRequest, HttpServletResponse response) {
		try {
			JwtResponse jwtResponse = authService.createNewUser(regRequest);
			return getMapResponseEntity(response, jwtResponse);
		} catch (UserAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAuthToken(@CookieValue("jwt") String refreshToken, HttpServletResponse response) {
		try {
			JwtResponse jwtResponse = authService.refreshAuthToken(refreshToken);
			return getMapResponseEntity(response, jwtResponse);
		} catch (BadRefreshTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpServletResponse response, JwtResponse jwtResponse) {
		jwtTokenProvider.setTokenCookies(response, jwtResponse);
		Map<String, Object> responseBody = createJwtResponseBody(jwtResponse);
		return ResponseEntity.ok(responseBody);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(@CookieValue("jwt") String refreshToken, HttpServletResponse response) {
		try {
			authService.logoutUser(refreshToken);
			response.setHeader("Set-Cookie", "jwt=; HttpOnly; SameSite=None; Secure; Max-age=0");
			return ResponseEntity.noContent().build();
		} catch (EmptyArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	private Map<String, Object> createJwtResponseBody(JwtResponse jwtResponse) {
		Map<String, Object> responseBody = new HashMap<>();
		List<Integer> roleValue = jwtResponse.getRoles().stream()
				.map(Role::getValue)
				.toList();

		responseBody.put("roles", roleValue);
		responseBody.put("accessToken", jwtResponse.getJwtAccessToken());
		return responseBody;
	}
}
