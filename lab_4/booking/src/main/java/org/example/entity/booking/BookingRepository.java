package org.example.entity.booking;

import org.example.entity.apartment.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>,
		JpaSpecificationExecutor<Booking> {

	boolean existsByApartmentAndStartDateBetween(Apartment apartment, LocalDate startDate, LocalDate endDate);

	List<Booking> findByApartment(Apartment apartment);
}
