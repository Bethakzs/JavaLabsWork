package org.example.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.service.UserService;
import org.example.validation.annotation.UniquePhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

	private final UserService userService;

	public UniquePhoneNumberValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		return phoneNumber != null && !userService.existsByPhoneNumber(phoneNumber);
	}
}

