package org.example.service;

import org.example.entity.amenity.AmenityType;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ApartmentService {

	List<Apartment> searchApartment(AmenityType amenityType, Type type);

	BigDecimal getTotalPriceOfApartment(Apartment apartment, LocalDate startDate, LocalDate endDate);

	String getStatistics(Long id);

	Apartment findApartmentById(Long apartmentId);
}
