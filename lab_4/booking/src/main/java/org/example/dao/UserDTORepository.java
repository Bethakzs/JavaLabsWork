package org.example.dao;

import org.example.entity.booking.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserDTORepository extends JpaRepository<UserDTO, Long>,
		JpaSpecificationExecutor<UserDTO> {
	Optional<UserDTO> findByEmail(String email);
}
