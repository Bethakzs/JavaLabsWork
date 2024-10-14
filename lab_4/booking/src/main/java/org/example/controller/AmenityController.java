package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.amenity.Amenity;
import org.example.service.AmenityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/apartment/amenity")
@Slf4j
public class AmenityController {

	private final AmenityService amenityService;

	@GetMapping("/all")
	public ResponseEntity<List<Amenity>> getAllAmenities() {
		List<Amenity> amenities = amenityService.getAllAmenities();
		if (amenities.isEmpty()) {
			log.warn("No amenities found");
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(amenities);
	}
}
