package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.response.JwtResponse;
import org.example.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.accessToken.lifetime}")
	private Duration jwtAccessTokenLifetime;

	@Value("${jwt.refreshToken.lifetime}")
	private Duration jwtRefreshTokenLifetime;

	public void setTokenCookies(HttpServletResponse response, JwtResponse jwtResponse) {
		String cookieValue = String.format(
				"jwt=%s; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=%d",
				jwtResponse.getJwtRefreshToken(), jwtRefreshTokenLifetime.toSeconds());
		response.setHeader("Set-Cookie", cookieValue);
	}

	public boolean validateToken(String token, String email) {
		return email.equals(getEmail(token)) && !isTokenExpired(token);
	}

	public String getEmail(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public List<Role> getRoles(String token) {
		@SuppressWarnings("unchecked")
		List<String> roleStrings = (List<String>) getAllClaimsFromToken(token).get("roles");
		return roleStrings != null ? roleStrings.stream().map(Role::valueOf).toList() : Collections.emptyList();
	}

	private Boolean isTokenExpired(String token) {
		return getAllClaimsFromToken(token).getExpiration().before(new Date());
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	public String generateAccessToken(UserDetails userDetails) {
		return generateJwt(userDetails, jwtAccessTokenLifetime);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return generateJwt(userDetails, jwtRefreshTokenLifetime);
	}

	private String generateJwt(UserDetails userDetails, Duration lifetime) {
		Date issuedDate = new Date();
		Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());
		return Jwts.builder()
				.setHeader(createJwtHeader())
				.setClaims(createClaims(userDetails))
				.setSubject(userDetails.getUsername())
				.setIssuedAt(issuedDate)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	private Map<String, Object> createJwtHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		return header;
	}

	private Map<String, Object> createClaims(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		Set<Role> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.map(Role::valueOf)
				.collect(Collectors.toSet());
		claims.put("roles", roles);
		return claims;
	}
}
