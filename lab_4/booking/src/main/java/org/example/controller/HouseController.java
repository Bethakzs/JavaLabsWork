package org.example.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.HouseRequestDTO;
import org.example.entity.apartment.House;
import org.example.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/apartment/house")
@Slf4j
public class HouseController {

	private final HouseService houseService;

	@GetMapping("/{id}")
	public ResponseEntity<House> getHouse(@PathVariable Long id) {
		House house = houseService.findHouseById(id);
		logHouseInfo(house);
		return ResponseEntity.ok(house);
	}

	@GetMapping()
	public ResponseEntity<List<House>> getAllHouses() {
		List<House> houses = houseService.getAllHouse();
		if (houses.isEmpty()) {
			logNoHousesFound();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(houses);
	}

	@PostMapping()
	public ResponseEntity<House> createHouse(@Valid @RequestBody HouseRequestDTO houseRequestDTO) {
		House house = houseService.createHouse(houseRequestDTO);
		logHouseInfo(house);
		return ResponseEntity.ok(house);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed({"ROLE_EDITOR", "EDITOR"})
	public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
		houseService.deleteHouse(id);
		log.info("House with ID {} was deleted.", id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<House> updateHouse(
			@Valid @RequestBody HouseRequestDTO houseRequestDTO, @PathVariable Long id) {
		House house = houseService.updateHouse(houseRequestDTO, id);
		logHouseInfo(house);
		return ResponseEntity.ok(house);
	}

	private static void logHouseInfo(House house) {
		log.info("House: ID={}, Name={}, Price={}",
				house.getId(), house.getName(), house.getPrice());
	}

	private static void logNoHousesFound() {
		log.warn("No houses found in the system.");
	}
}
