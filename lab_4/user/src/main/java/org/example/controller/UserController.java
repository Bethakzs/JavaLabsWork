package org.example.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RolesAllowed({"USER", "ADMIN", "EDITOR"})
@RequestMapping("v1/api/users")
@Slf4j
public class UserController {

	private final UserService userService;

	@GetMapping("/get")
	public ResponseEntity<UserDTO> getUser(Principal principal) {
		logPrincipal(principal);
		User user = userService.findByEmail(principal.getName());
		UserDTO userDTO = mapUserToUserDTO(user);
		logUserDTO(userDTO);
		return ResponseEntity.ok(userDTO);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteUser(Principal principal) {
		logPrincipal(principal);
		userService.deleteUser(principal.getName());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserRegistration userRegistration, Principal principal) {
		logPrincipal(principal);
		User user = userService.updateUser(principal.getName(), userRegistration);
		UserDTO userDTO = mapUserToUserDTO(user);
		logUserDTO(userDTO);
		return ResponseEntity.ok(userDTO);
	}

	private UserDTO mapUserToUserDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.balance(user.getBalance())
				.build();
	}

	private void logPrincipal(Principal principal) {
		log.info("User principal data: {}", principal.getName());
	}

	private void logUserDTO(UserDTO user) {
		log.info("User data: {}", user);
	}
}
