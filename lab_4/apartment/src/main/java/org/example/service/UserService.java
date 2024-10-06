package org.example.service;

import org.example.dto.request.UserRegistration;
import org.example.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

	User createUser(UserRegistration registrationDTO);

	void updateUser(User user);

	User findByEmail(String email);

	boolean isAvailableUser(UserRegistration registrationDTO);

	Optional<User> findByRefreshToken(String refreshToken);
}
