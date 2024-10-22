package org.example.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.validator.UniquePhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
public @interface UniquePhoneNumber {
	String message() default "Phone number is already taken";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
