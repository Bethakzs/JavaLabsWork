package org.example.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
}
