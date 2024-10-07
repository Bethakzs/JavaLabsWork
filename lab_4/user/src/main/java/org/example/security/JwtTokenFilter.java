package org.example.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		String jwt = extractToken(request);
		if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			authenticateUser(jwt);
		}
		filterChain.doFilter(request, response);
	}


	private String extractToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String jwt = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
		}
		return jwt;
	}

	private void authenticateUser(String jwt) {
		try {
			String email = jwtTokenService.getEmail(jwt);
			List<Role> roles = jwtTokenService.getRoles(jwt);
			log.debug("Roles: {}", roles);
			List<SimpleGrantedAuthority> authorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.toString()))
					.toList();
			var token = new UsernamePasswordAuthenticationToken(email, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(token);
		} catch (ExpiredJwtException e) {
			logger.debug("Time of token expired");
		} catch (SignatureException e) {
			logger.debug("Invalid token signature");
		}
	}
}