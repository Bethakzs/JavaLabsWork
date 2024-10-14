package org.example.service.impl;

import org.example.dao.UserRepository;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.UserDTO;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.EmptyArgumentException;
import org.example.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private KafkaTemplate<String, UserDTO> kafkaTemplate;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void givenValidEmail_loadUserByUsername_shouldReturnUserDetails() {
		String email = "test@example.com";
		User user = buildUser(buildUserRegistration());

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		UserDetails result = userService.loadUserByUsername(email);

		assertThat(result.getUsername()).isEqualTo(email);
		assertThat(result.getPassword()).isEqualTo(user.getPassword());
		assertThat(result.getAuthorities()).hasSize(1);
		assertThat(result.getAuthorities().iterator().next().getAuthority()).isEqualTo(Role.ROLE_USER.name());
	}

	@Test
	void givenInvalidEmail_loadUserByUsername_shouldThrowUserNotFoundException() {
		String email = "invalid@example.com";

		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername(email));
	}


	@Test
	void givenUserRegistration_createUser_shouldSaveUser() {
		UserRegistration registration = buildUserRegistration();
		Role role = Role.ROLE_USER;

		User newUser = new User(null, registration.getFirstName(), registration.getLastName(),
				registration.getEmail(), "encodedPassword",
				registration.getPhoneNumber(), BigDecimal.ZERO, null,
				Collections.singleton(role));

		User savedUser = new User(1L, registration.getFirstName(), registration.getLastName(),
				registration.getEmail(), "encodedPassword",
				registration.getPhoneNumber(), BigDecimal.ZERO, null,
				Collections.singleton(role));

		when(passwordEncoder.encode(registration.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(newUser)).thenReturn(savedUser);

		User result = userService.createUser(registration, role);

		assertThat(result).isEqualTo(savedUser);

		verify(userRepository).save(newUser);

		verify(kafkaTemplate).send(eq("email-registration-topic"),
				argThat(userDTO -> userDTO.getEmail().equals(savedUser.getEmail()) &&
						userDTO.getLastName().equals(savedUser.getLastName()) &&
						userDTO.getFirstName().equals(savedUser.getFirstName())));
	}


	@Test
	void givenUser_updateUser_shouldSaveUser() {
		User user = buildUser(buildUserRegistration());

		userService.updateUser(user);

		verify(userRepository).save(user);
	}


	@Test
	void givenEmail_updateUser_shouldUpdateUser() {
		String email = "test@example.com";
		UserRegistration registration = buildUserRegistration();
		User existingUser = buildUser(registration);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(existingUser)).thenReturn(existingUser);

		User result = userService.updateUser(email, registration);

		assertThat(result).isEqualTo(existingUser);
		verify(userRepository).save(existingUser);
	}

	@Test
	void givenUserDTOWithNullPassword_updateUser_shouldNotChangePassword() {
		String email = "user@example.com";
		User user = buildUser(buildUserRegistration());
		UserRegistration userDTO = new UserRegistration();
		userDTO.setFirstName("NewFirstName");
		userDTO.setLastName("NewLastName");
		userDTO.setEmail("new.email@example.com");
		userDTO.setPhoneNumber("0987654321");
		userDTO.setPassword(null);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		User updatedUser = userService.updateUser(email, userDTO);

		assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
		assertThat(updatedUser.getFirstName()).isEqualTo("NewFirstName");
		assertThat(updatedUser.getLastName()).isEqualTo("NewLastName");
		assertThat(updatedUser.getEmail()).isEqualTo("new.email@example.com");
		assertThat(updatedUser.getPhoneNumber()).isEqualTo("0987654321");

		verify(userRepository, times(1)).save(updatedUser);
	}


	@Test
	void givenEmail_deleteUser_shouldRemoveUser() {
		String email = "test@example.com";
		User existingUser = buildUser(buildUserRegistration());

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

		userService.deleteUser(email);

		verify(userRepository).delete(existingUser);
	}

	@Test
	void givenInvalidEmail_deleteUser_shouldThrowException() {
		String email = "invalid@example.com";

		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

		assertThrows(EmptyArgumentException.class, () -> userService.deleteUser(email));
	}


	@Test
	void givenEmail_findByEmail_shouldReturnUser() {
		String email = "test@example.com";
		User existingUser = buildUser(buildUserRegistration());

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

		User result = userService.findByEmail(email);

		assertThat(result).isEqualTo(existingUser);
	}

	@Test
	void givenInvalidEmail_findByEmail_shouldThrowUserNotFoundException() {
		String email = "invalid@example.com";

		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.findByEmail(email));
	}


	@Test
	void givenUser_updateEmail_shouldChangeEmail() throws Exception {
		User user = buildUser(buildUserRegistration());
		String newEmail = "new@example.com";

		when(userRepository.existsByEmail(newEmail)).thenReturn(false);

		Method method = UserServiceImpl.class.getDeclaredMethod("updateEmail", User.class, String.class);
		method.setAccessible(true);
		method.invoke(userService, user, newEmail);

		assertThat(user.getEmail()).isEqualTo(newEmail);
	}

	@Test
	void givenExistingEmail_updateEmail_shouldThrowException() throws Exception {
		User user = buildUser(buildUserRegistration());
		String newEmail = "existing@example.com";

		when(userRepository.existsByEmail(newEmail)).thenReturn(true);

		Method method = UserServiceImpl.class.getDeclaredMethod("updateEmail", User.class, String.class);
		method.setAccessible(true);

		EmptyArgumentException exception = assertThrows(EmptyArgumentException.class, () -> {
			try {
				method.invoke(userService, user, newEmail);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		});

		assertThat(exception.getMessage()).isEqualTo("Email already in use");
	}


	@Test
	void givenUser_updatePhoneNumber_shouldChangePhoneNumber() throws Exception {
		User user = buildUser(buildUserRegistration());
		String newPhoneNumber = "0987654321";

		when(userRepository.existsByPhoneNumber(newPhoneNumber)).thenReturn(false);

		Method method = UserServiceImpl.class.getDeclaredMethod("updatePhoneNumber", User.class, String.class);
		method.setAccessible(true);
		method.invoke(userService, user, newPhoneNumber);

		assertThat(user.getPhoneNumber()).isEqualTo(newPhoneNumber);
	}


	@Test
	void givenValidRefreshToken_findByRefreshToken_shouldReturnUser() {
		String refreshToken = "validToken";
		User user = buildUser(buildUserRegistration());

		when(userRepository.findByRefreshToken(refreshToken)).thenReturn(Optional.of(user));

		Optional<User> result = userService.findByRefreshToken(refreshToken);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(user);
	}


	@Test
	void givenNewUser_isAvailableUser_shouldReturnTrue() {
		UserRegistration registrationDTO = buildUserRegistration();

		when(userRepository.findByEmail(registrationDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.findByPhoneNumber(registrationDTO.getPhoneNumber())).thenReturn(Optional.empty());

		boolean result = userService.isAvailableUser(registrationDTO);

		assertThat(result).isTrue();
	}

	@Test
	void givenExistingEmail_isAvailableUser_shouldReturnFalse() {
		UserRegistration registrationDTO = new UserRegistration();
		registrationDTO.setEmail("existing@example.com");
		registrationDTO.setPhoneNumber("0987654321");

		when(userRepository.findByEmail(registrationDTO.getEmail())).thenReturn(Optional.of(new User()));

		boolean isAvailable = userService.isAvailableUser(registrationDTO);

		assertThat(isAvailable).isFalse();
	}


	@Test
	void givenValidUserId_findById_shouldReturnUser() {
		Long userId = 1L;
		User expectedUser = buildUser(buildUserRegistration());

		when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

		User result = userService.findById(userId);

		assertThat(result).isEqualTo(expectedUser);
	}


	@Test
	void givenNewPhoneNumber_updatePhoneNumber_shouldChangePhoneNumber() throws Exception {
		User user = buildUser(buildUserRegistration());
		String newPhoneNumber = "0987654321";

		when(userRepository.existsByPhoneNumber(newPhoneNumber)).thenReturn(false);

		Method method = UserServiceImpl.class.getDeclaredMethod("updatePhoneNumber", User.class, String.class);
		method.setAccessible(true);

		method.invoke(userService, user, newPhoneNumber);

		assertThat(user.getPhoneNumber()).isEqualTo(newPhoneNumber);
	}

	@Test
	void givenExistingPhoneNumber_updatePhoneNumber_shouldThrowEmptyArgumentException() throws Exception {
		User user = buildUser(buildUserRegistration());
		String newPhoneNumber = "0987654321";

		when(userRepository.existsByPhoneNumber(newPhoneNumber)).thenReturn(true);

		Method method = UserServiceImpl.class.getDeclaredMethod("updatePhoneNumber", User.class, String.class);
		method.setAccessible(true);

		Exception exception = assertThrows(InvocationTargetException.class, () -> {
			method.invoke(userService, user, newPhoneNumber);
		});

		Throwable cause = exception.getCause();
		assertThat(cause).isInstanceOf(EmptyArgumentException.class);
		assertThat(cause.getMessage()).isEqualTo("Phone number already in use");
	}

	@Test
	void givenNullPhoneNumber_updatePhoneNumber_shouldNotChangePhoneNumber() throws Exception {
		User user = buildUser(buildUserRegistration());
		String oldPhoneNumber = user.getPhoneNumber();

		Method method = UserServiceImpl.class.getDeclaredMethod("updatePhoneNumber", User.class, String.class);
		method.setAccessible(true);

		method.invoke(userService, user, null);

		assertThat(user.getPhoneNumber()).isEqualTo(oldPhoneNumber);
	}


	@Test
	void givenNullEmail_updateEmail_shouldNotChangeEmail() throws Exception {
		User user = buildUser(buildUserRegistration());
		String oldEmail = user.getEmail();

		Method method = UserServiceImpl.class.getDeclaredMethod("updateEmail", User.class, String.class);
		method.setAccessible(true);

		method.invoke(userService, user, null);

		assertThat(user.getEmail()).isEqualTo(oldEmail);
	}


	@Test
	void givenNullValue_updateField_shouldNotCallSetter() throws Exception {
		Consumer<String> mockSetter = Mockito.mock(Consumer.class);

		Method method = UserServiceImpl.class.getDeclaredMethod("updateField", Object.class, Consumer.class);
		method.setAccessible(true);
		method.invoke(userService, null, mockSetter);

		verify(mockSetter, never()).accept(any(String.class));
	}

	private UserRegistration buildUserRegistration() {
		return UserRegistration.builder()
				.firstName("John")
				.lastName("Doe")
				.email("test@example.com")
				.phoneNumber("1234567890")
				.password("password")
				.build();
	}

	private User buildUser(UserRegistration registration) {
		return User.builder()
				.id(1L)
				.firstName(registration.getFirstName())
				.lastName(registration.getLastName())
				.email(registration.getEmail())
				.phoneNumber(registration.getPhoneNumber())
				.password("encodedPassword")
				.balance(BigDecimal.ZERO)
				.roles(new HashSet<>(Collections.singletonList(Role.ROLE_USER)))
				.build();
	}
}
