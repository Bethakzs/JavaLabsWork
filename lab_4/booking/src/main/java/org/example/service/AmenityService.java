package org.example.service;

import org.example.entity.amenity.Amenity;

import java.util.List;
import java.util.Optional;

public interface AmenityService {

	List<Amenity> getAllAmenities();

	Optional<Amenity> findById(Long amenityId);
}
