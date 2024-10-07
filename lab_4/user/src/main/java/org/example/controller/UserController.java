package org.example.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserRegistration;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.example.exception.EmptyArgumentException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public ResponseEntity<?> getUser(Principal principal) {
		try {
			logPrincipal(principal);
			User user = userService.findByEmail(principal.getName());
			UserDTO userDTO = mapUserToUserDTO(user);
			logUserDTO(userDTO);
			return ResponseEntity.ok(userDTO);
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(Principal principal) {
		try {
			logPrincipal(principal);
			userService.deleteUser(principal.getName());
			return ResponseEntity.noContent().build();
		} catch (EmptyArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody UserRegistration userRegistration, Principal principal) {
		try {
			logPrincipal(principal);
			User user = userService.updateUser(principal.getName(), userRegistration);
			UserDTO userDTO = mapUserToUserDTO(user);
			logUserDTO(userDTO);
			return ResponseEntity.ok(userDTO);
		} catch (UsernameNotFoundException | EmptyArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
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
