package org.example.service;

import org.example.dto.request.UserRegistration;
import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

	User createUser(UserRegistration registrationDTO);

	void updateUser(User user);

	User updateUser(String name, UserRegistration userDTO);

	void deleteUser(String name);

	User findByEmail(String email);

	Optional<User> findByRefreshToken(String refreshToken);

	boolean isAvailableUser(UserRegistration registrationDTO);
}
