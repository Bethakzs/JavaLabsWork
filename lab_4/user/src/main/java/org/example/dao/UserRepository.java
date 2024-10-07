package org.example.dao;

import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query("SELECT u FROM User u WHERE u.refreshToken = :refreshToken")
	Optional<User> findByRefreshToken(@Param("refreshToken") String refreshToken);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
	Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
	boolean existsByEmail(String email);

	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.phoneNumber = :phoneNumber")
	boolean existsByPhoneNumber(String phoneNumber);

}
