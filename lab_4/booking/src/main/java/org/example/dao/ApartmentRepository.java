package org.example.dao;

import org.example.entity.apartment.Apartment;
import org.example.entity.booking.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>,
		JpaSpecificationExecutor<UserDTO> {
}

