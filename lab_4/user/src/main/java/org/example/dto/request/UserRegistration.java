package org.example.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.annotation.UniqueEmail;
import org.example.validation.annotation.UniquePhoneNumber;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email format, must be a valid email address with an @ character and a correct domain")
	@UniqueEmail
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;

	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp = "^\\+?[0-9 ]{7,25}$", message = "Invalid phone number format, must be at least 7 characters long")
	@UniquePhoneNumber
	private String phoneNumber;
}
