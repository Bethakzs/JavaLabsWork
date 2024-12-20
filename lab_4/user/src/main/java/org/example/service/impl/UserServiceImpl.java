package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserRepository;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.UserDTO;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.EmptyArgumentException;
import org.example.exception.UserNotFoundException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private static final String USER_NOT_FOUND = "User not found";
	private static final String USER_REGISTRATION_TOPIC = "email-registration-topic";

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final KafkaTemplate<String, UserDTO> kafkaTemplate;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(HttpStatus.UNAUTHORIZED.value(), USER_NOT_FOUND));

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	@Transactional
	public User createUser(UserRegistration userRegistration, Role role) {
		User user = User.builder()
				.firstName(userRegistration.getFirstName())
				.lastName(userRegistration.getLastName())
				.email(userRegistration.getEmail())
				.phoneNumber(userRegistration.getPhoneNumber())
				.password(passwordEncoder.encode(userRegistration.getPassword()))
				.balance(BigDecimal.ZERO)
				.roles(new HashSet<>(Collections.singletonList(role)))
				.build();
		User savedUser = userRepository.save(user);

		UserDTO userDTO = convertToUserDTO(savedUser);
		kafkaTemplate.send(USER_REGISTRATION_TOPIC, userDTO);
		log.info("User registered and message sent to Kafka: {} with topic {}",
				savedUser.getEmail(), USER_REGISTRATION_TOPIC);
		return savedUser;
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(String email, UserRegistration userDTO) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EmptyArgumentException(HttpStatus.BAD_REQUEST.value(), USER_NOT_FOUND));

		updateField(userDTO.getFirstName(), user::setFirstName);
		updateField(userDTO.getLastName(), user::setLastName);
		updateEmail(user, userDTO.getEmail());
		updatePhoneNumber(user, userDTO.getPhoneNumber());

		if (userDTO.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}

		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteUser(String name) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new EmptyArgumentException(HttpStatus.BAD_REQUEST.value(), USER_NOT_FOUND));
		userRepository.delete(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
	}

	@Override
	public Optional<User> findByRefreshToken(String refreshToken) {
		return userRepository.findByRefreshToken(refreshToken);
	}

	@Override
	public boolean isAvailableUser(UserRegistration registrationDTO) {
		return userRepository.findByEmail(registrationDTO.getEmail()).isEmpty()
				&& userRepository.findByPhoneNumber(registrationDTO.getPhoneNumber()).isEmpty();
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));
	}

	@Override
	@Transactional
	public void withdrawBalance(String email, BigDecimal amount) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));

		BigDecimal newBalance = user.getBalance().subtract(amount);
		user.setBalance(newBalance);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User depositBalance(String name, BigDecimal amount) {
		User user = userRepository.findByEmail(name)
				.orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.value(), USER_NOT_FOUND));

		BigDecimal newBalance = user.getBalance().add(amount);
		user.setBalance(newBalance);
		return userRepository.save(user);
	}

	private static UserDTO convertToUserDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.balance(user.getBalance())
				.build();
	}

	private <T> void updateField(T newValue, Consumer<T> setter) {
		if (newValue != null) {
			setter.accept(newValue);
		}
	}

	private void updateEmail(User user, String newEmail) {
		if (newEmail != null && !user.getEmail().equals(newEmail)) {
			if (existsByEmail(newEmail)) {
				throw new EmptyArgumentException(HttpStatus.CONFLICT.value(), "Email already in use");
			}
			user.setEmail(newEmail);
		}
	}

	private void updatePhoneNumber(User user, String newPhoneNumber) {
		if (newPhoneNumber != null && !user.getPhoneNumber().equals(newPhoneNumber)) {
			if (existsByPhoneNumber(newPhoneNumber)) {
				throw new EmptyArgumentException(HttpStatus.CONFLICT.value(), "Phone number already in use");
			}
			user.setPhoneNumber(newPhoneNumber);
		}
	}
}
