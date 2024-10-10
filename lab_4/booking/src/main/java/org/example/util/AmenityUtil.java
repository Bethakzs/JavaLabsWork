package org.example.util;

import lombok.RequiredArgsConstructor;
import org.example.entity.amenity.Amenity;
import org.example.exception.AmenityNotExistException;
import org.example.service.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AmenityUtil {

	private final AmenityService amenityService;

	public List<Amenity> getAmenities(List<Long> amenityIds) {
		return Optional.ofNullable(amenityIds)
				.orElseGet(Collections::emptyList).stream()
				.map(amenityId -> amenityService.findById(amenityId)
						.orElseThrow(() -> new AmenityNotExistException(HttpStatus.NOT_FOUND.value(),
								"Amenity with ID " + amenityId + " does not exist")))
				.toList();
	}

	public List<Amenity> findAllById(List<Long> amenityIds) {
		return amenityService.findAllById(amenityIds);
	}
}
