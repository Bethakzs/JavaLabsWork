package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.request.UserRegistration;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.EmailNotFoundException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EmailNotFoundException(HttpStatus.UNAUTHORIZED.value(), "User not found"));

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public Optional<User> findByRefreshToken(String refreshToken) {
		return userRepository.findByRefreshToken(refreshToken);
	}

	@Transactional
	public User createUser(UserRegistration userRegistration) {
		User user = User.builder()
				.firstName(userRegistration.getFirstName())
				.lastName(userRegistration.getLastName())
				.email(userRegistration.getEmail())
				.phoneNumber(userRegistration.getPhoneNumber())
				.password(passwordEncoder.encode(userRegistration.getPassword()))
				.balance(BigDecimal.ZERO)
				.roles(new HashSet<>(Collections.singletonList(Role.ROLE_USER)))
				.build();

		// TODO: make confirm email that user is registered
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public boolean isAvailableUser(UserRegistration registrationDTO) {
		return userRepository.findByEmail(registrationDTO.getEmail()).isEmpty()
				&& userRepository.findByPhoneNumber(registrationDTO.getPhoneNumber()).isEmpty();
	}
}
