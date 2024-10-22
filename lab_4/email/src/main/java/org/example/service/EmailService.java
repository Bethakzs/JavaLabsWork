package org.example.service;

import org.example.dto.request.UserDTO;

public interface EmailService {

	void send(UserDTO userDTO);
}
