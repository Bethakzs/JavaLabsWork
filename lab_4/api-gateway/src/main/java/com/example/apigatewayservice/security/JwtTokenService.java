package com.example.apigatewayservice.security;

import com.example.apigatewayservice.dto.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class JwtTokenService {

	private final String secret;

	@Autowired
	public JwtTokenService(@Value("${jwt.secret}") String secret) {
		this.secret = secret;
	}

	public String getEmail(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public List<Role> getRoles(String token) {
		@SuppressWarnings("unchecked")
		List<String> roleStrings = (List<String>) getAllClaimsFromToken(token).get("roles");
		return roleStrings != null ? roleStrings.stream().map(Role::valueOf).toList() : Collections.emptyList();
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
}
