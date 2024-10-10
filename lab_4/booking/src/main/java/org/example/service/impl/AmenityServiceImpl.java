package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.AmenityRepository;
import org.example.entity.amenity.Amenity;
import org.example.service.AmenityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

	private final AmenityRepository amenityRepository;

	@Override
	public List<Amenity> getAllAmenities() {
		return amenityRepository.findAll();
	}

	@Override
	public List<Amenity> findAllById(List<Long> amenityIds) {
		return amenityRepository.findAllById(amenityIds);
	}

	@Override
	public Optional<Amenity> findById(Long amenityId) {
		return amenityRepository.findById(amenityId);
	}
}
