package org.example.service;

import org.example.entity.amenity.AmenityType;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.Type;

import java.util.List;

public interface ApartmentService {

	List<Apartment> searchApartment(AmenityType amenityType, Type type);

	String getStatistics(Long id);

	Apartment findApartmentById(Long apartmentId);
}
