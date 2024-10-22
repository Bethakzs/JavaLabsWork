package com.example.apigatewayservice.config;

import com.example.apigatewayservice.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenFilter jwtTokenFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		return http
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchanges -> exchanges
						.pathMatchers("/api/v1/auth/**").permitAll()
						.pathMatchers(HttpMethod.PUT, "/api/v1/apartment/house/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/v1/apartment/house/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers(HttpMethod.PUT, "/api/v1/apartment/hotel/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/v1/apartment/hotel/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers(HttpMethod.PUT, "/api/v1/apartment/room/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers(HttpMethod.DELETE, "/api/v1/apartment/room/**").hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers("/api/v1/users/**").hasAnyRole("USER", "EDITOR", "ADMIN")
						.pathMatchers("/api/v1/apartment/**").hasAnyRole("USER", "EDITOR", "ADMIN")
						.pathMatchers("/api/v1/booking/**").hasAnyRole("USER", "EDITOR", "ADMIN")
				)
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.exceptionHandling(e -> e.authenticationEntryPoint((swe, ex) -> Mono.fromRunnable(() -> {
					swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				})))
				.addFilterAt(jwtTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}
}
