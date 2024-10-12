package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.HotelRequestDTO;
import org.example.entity.apartment.Hotel;
import org.example.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/apartment/hotel")
@Slf4j
public class HotelController {

	private final HotelService hotelService;

	@GetMapping("/get/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
		Hotel hotel = hotelService.findHotelById(id);
		logHotelInfo(hotel);
		return ResponseEntity.ok(hotel);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<Hotel>> getAllHotels() {
		List<Hotel> hotels = hotelService.getAllHotels();
		if (hotels.isEmpty()) {
			logNoHotelsFound();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(hotels);
	}

	@PostMapping("/create")
	public ResponseEntity<Hotel> createHotel(@Valid @RequestBody HotelRequestDTO hotelRequestDTO) {
		Hotel hotel = hotelService.createHotel(hotelRequestDTO);
		logHotelInfo(hotel);
		return ResponseEntity.ok(hotel);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
		hotelService.deleteHotel(id);
		log.info("House with ID {} was deleted.", id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Hotel> updateHotel(
			@Valid @RequestBody HotelRequestDTO hotelRequestDTO, @PathVariable Long id) {
		Hotel hotel = hotelService.updateHotel(hotelRequestDTO, id);
		logHotelInfo(hotel);
		return ResponseEntity.ok(hotel);
	}

	private static void logHotelInfo(Hotel hotel) {
		log.info("Hotel: ID={}, Name={}, Price={}",
				hotel.getId(), hotel.getName(), hotel.getPrice());
	}

	private static void logNoHotelsFound() {
		log.warn("No hotels found in the system.");
	}
}
