package com.example.apigatewayservice.security;

import com.example.apigatewayservice.dto.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtTokenService {

	@Value("${jwt.secret}")
	private String secret;

	public String getEmail(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public List<Role> getRoles(String token) {
		return ((List<String>) getAllClaimsFromToken(token).get("roles"))
				.stream()
				.map(Role::valueOf)
				.toList();
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
}
