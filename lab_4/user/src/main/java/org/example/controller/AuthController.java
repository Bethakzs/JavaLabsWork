package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.JwtRequest;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.JwtResponse;
import org.example.entity.Role;
import org.example.security.JwtTokenProvider;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> createAuthToken(@Valid @RequestBody JwtRequest authRequest, HttpServletResponse response) {
		JwtResponse jwtResponse = authService.createAuthToken(authRequest);
		return getMapResponseEntity(response, jwtResponse);
	}

	@PostMapping("/registration")
	public ResponseEntity<Map<String, Object>> createNewUser(@Valid @RequestBody UserRegistration regRequest, HttpServletResponse response) {
		JwtResponse jwtResponse = authService.createNewUser(regRequest);
		return getMapResponseEntity(response, jwtResponse);
	}

	@PostMapping("/refresh")
	public ResponseEntity<Map<String, Object>> refreshAuthToken(@CookieValue("jwt") String refreshToken, HttpServletResponse response) {
		JwtResponse jwtResponse = authService.refreshAuthToken(refreshToken);
		return getMapResponseEntity(response, jwtResponse);
	}

	private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpServletResponse response, JwtResponse jwtResponse) {
		jwtTokenProvider.setTokenCookies(response, jwtResponse);
		Map<String, Object> responseBody = createJwtResponseBody(jwtResponse);
		return ResponseEntity.ok(responseBody);
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

	@PostMapping("/logout")
	public ResponseEntity<Void> userLogout(@CookieValue("jwt") String refreshToken, HttpServletResponse response) {
		authService.logoutUser(refreshToken);
		response.setHeader("Set-Cookie", "jwt=; HttpOnly; SameSite=None; Secure; Max-age=0");
		return ResponseEntity.noContent().build();
	}
}
