package org.example.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.service.UserService;
import org.example.validation.annotation.UniqueEmail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserService userService;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return email != null && !userService.existsByEmail(email);
	}
}

