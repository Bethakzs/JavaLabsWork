package com.example.apigatewayservice.config;

import com.example.apigatewayservice.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
						.pathMatchers("/v1/api/auth/**").permitAll()
						.pathMatchers("/v1/api/users/**").hasAnyRole("USER", "EDITOR", "ADMIN")
						.pathMatchers(
								"/v1/api/booking/house/update/**",
								"/v1/api/booking/house/delete/**",
								"/v1/api/booking/hotel/update/**",
								"/v1/api/booking/hotel/delete/**",
								"/v1/api/booking/hotel/room/update/**",
								"/v1/api/booking/hotel/room/delete/**"
						).hasAnyRole("EDITOR", "ADMIN")
						.pathMatchers("/v1/api/booking/**").hasAnyRole("USER", "EDITOR", "ADMIN")
				)
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.exceptionHandling(e -> e.authenticationEntryPoint((swe, ex) -> Mono.fromRunnable(() -> {
					swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				})))
				.addFilterAt(jwtTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}
}
