package org.example.entity.booking;

import lombok.RequiredArgsConstructor;
import org.example.entity.amenity.AmenityType;
import org.example.entity.apartment.Apartment;
import org.example.entity.apartment.House;
import org.example.entity.apartment.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/apartment")
@RequiredArgsConstructor
public class ApartmentController {

	private final ApartmentServiceImpl apartmentService;

	@GetMapping("/search")
	public ResponseEntity<List<House>> searchApartments(
			@RequestParam(required = false) AmenityType amenityType,
			@RequestParam(required = false) Type type) {
		List<House> apartments = apartmentService.searchApartment(amenityType, type);
		if (apartments.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(apartments);
	}

	@GetMapping("/statistics/{id}")
	public ResponseEntity<String> getStatistics(@PathVariable Long id) {
		String statistics = apartmentService.getStatistics(id);
		return ResponseEntity.ok(statistics);
	}

}

