package org.example.dao;

import org.example.entity.apartment.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>,
		JpaSpecificationExecutor<House> {
}
