package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private BigDecimal balance;
}
