package org.example.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.service.UserService;
import org.example.validation.annotation.UniqueEmail;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserService userService;

	public UniqueEmailValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return email != null && !userService.existsByEmail(email);
	}
}

