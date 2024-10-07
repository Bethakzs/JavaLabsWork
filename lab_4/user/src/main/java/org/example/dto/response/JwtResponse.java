package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.entity.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponse {
	private String jwtAccessToken;
	private String jwtRefreshToken;
	private Set<Role> roles;
}

