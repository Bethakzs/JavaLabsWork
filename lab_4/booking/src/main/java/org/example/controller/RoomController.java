package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.RoomRequestDTO;
import org.example.dto.response.RoomWithHotelAmenitiesDTO;
import org.example.entity.apartment.Room;
import org.example.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/apartment/room")
@Slf4j
public class RoomController {

	private final RoomService roomService;

	@GetMapping("/{roomId}")
	public ResponseEntity<RoomWithHotelAmenitiesDTO> getRoomInHotel(@PathVariable Long roomId) {
		RoomWithHotelAmenitiesDTO roomWithHotelAmenitiesDTO = roomService.findRoomWithHotelById(roomId);
		logRoomInfo(roomWithHotelAmenitiesDTO);
		return ResponseEntity.ok(roomWithHotelAmenitiesDTO);
	}

	@GetMapping()
	public ResponseEntity<List<Room>> getAllRooms() {
		List<Room> rooms = roomService.getAllRooms();
		if (rooms.isEmpty()) {
			logNoRoomsFound();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(rooms);
	}

	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<List<Room>> getAllRoomsInHotel(@PathVariable Long hotelId) {
		List<Room> rooms = roomService.getAllRoomsInHotel(hotelId);
		if (rooms.isEmpty()) {
			logNoRoomsFound();
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(rooms);
	}

	@PostMapping()
	public ResponseEntity<Room> addRoomInHotel(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
		Room room = roomService.addRoomToHotel(roomRequestDTO);
		logRoomInfo(room);
		return ResponseEntity.ok(room);
	}

	@DeleteMapping("/{roomId}")
	public ResponseEntity<Void> deleteRoomInHotel(@PathVariable Long roomId) {
		roomService.deleteRoomFromHotel(roomId);
		log.info("Room with ID {} was deleted.", roomId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{roomId}")
	public ResponseEntity<Room> updateRoomInHotel(
			@PathVariable Long roomId, @Valid @RequestBody RoomRequestDTO roomRequestDTO) {
		Room room = roomService.updateRoomInHotel(roomId, roomRequestDTO);
		logRoomInfo(room);
		return ResponseEntity.ok(room);
	}

	private static void logRoomInfo(Room room) {
		log.info("Room: ID={}, Name={}, Price={}",
				room.getId(), room.getName(), room.getPrice());
	}

	private static void logRoomInfo(RoomWithHotelAmenitiesDTO roomWithHotelAmenitiesDTO) {
		log.info("Room: ID={}, Name={}, Price={}, Hotel amenities={}",
				roomWithHotelAmenitiesDTO.getRoom().getId(),
				roomWithHotelAmenitiesDTO.getRoom().getName(),
				roomWithHotelAmenitiesDTO.getRoom().getPrice(),
				roomWithHotelAmenitiesDTO.getHotelAmenities());
	}

	private static void logNoRoomsFound() {
		log.warn("No rooms found in the system.");
	}
}
