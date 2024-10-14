package com.example.apigatewayservice.security;

import com.example.apigatewayservice.dto.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter implements WebFilter {

	private final JwtTokenService jwtTokenService;

	@Override
	public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
		return extractJwt(exchange)
				.flatMap(jwt -> processJwtToken(jwt, exchange, chain))
				.switchIfEmpty(chain.filter(exchange));
	}

	private @NonNull Mono<String> extractJwt(ServerWebExchange exchange) {
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
				.filter(authHeader -> authHeader.startsWith("Bearer "))
				.map(authHeader -> authHeader.substring(7));
	}

	private Mono<Void> processJwtToken(String jwt, ServerWebExchange exchange, WebFilterChain chain) {
		try {
			String email = jwtTokenService.getEmail(jwt);
			List<Role> roles = jwtTokenService.getRoles(jwt);
			List<SimpleGrantedAuthority> authorities = roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.name()))
					.collect(Collectors.toList());

			UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(email, null, authorities);
			SecurityContextImpl securityContext = new SecurityContextImpl(token);

			return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder
					.withSecurityContext(Mono.just(securityContext)));

		} catch (ExpiredJwtException e) {
			log.error("Token expired: {}", e.getMessage());
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();

		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();

		} catch (Exception e) {
			log.error("Error processing JWT: {}", e.getMessage());
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse().setComplete();
		}
	}
}
