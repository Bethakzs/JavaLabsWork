package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dao.RoomRepository;
import org.example.dto.request.RoomRequestDTO;
import org.example.dto.response.RoomWithHotelAmenitiesDTO;
import org.example.entity.amenity.Amenity;
import org.example.entity.apartment.Hotel;
import org.example.entity.apartment.Room;
import org.example.exception.RoomNotFoundException;
import org.example.service.HotelService;
import org.example.service.RoomService;
import org.example.util.AmenityUtil;
import org.example.util.UpdaterField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;
	private final HotelService hotelService;
	private final UpdaterField updaterField;
	private final AmenityUtil amenityUtil;

	@Override
	public Room findRoomById(Long id) {
		return roomRepository.findById(id)
				.orElseThrow(() -> new RoomNotFoundException(HttpStatus.NOT_FOUND.value(), "Room not found"));
	}

	@Override
	@Transactional
	public RoomWithHotelAmenitiesDTO findRoomWithHotelById(Long roomId) {
		Room room = findRoomById(roomId);
		Hotel hotel = room.getHotel();
		List<Amenity> hotelAmenities = hotel.getAmenities();

		return RoomWithHotelAmenitiesDTO.builder()
				.room(room)
				.hotelAmenities(hotelAmenities)
				.build();
	}

	@Override
	public List<Room> getAllRoomsInHotel(Long hotelId) {
		Hotel hotel = hotelService.findHotelById(hotelId);
		return hotel.getRooms();
	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	@Override
	@Transactional
	public Room addRoomToHotel(RoomRequestDTO roomRequestDTO) {
		Hotel hotel = hotelService.findHotelById(roomRequestDTO.getHotelId());
		List<Amenity> amenities = amenityUtil.findAllById(roomRequestDTO.getAmenityIds());

		Room room = new Room(roomRequestDTO.getName(), roomRequestDTO.getPrice(),
				roomRequestDTO.getType(), amenities, roomRequestDTO.getMaxSpace(), hotel);

		Room savedRoom = roomRepository.save(room);
		hotel.getRooms().add(room);
		hotelService.save(hotel);
		return savedRoom;
	}

	@Override
	@Transactional
	public void deleteRoomFromHotel(Long roomId) {
		Room room = findRoomById(roomId);
		Hotel hotel = room.getHotel();
		hotel.getRooms().remove(room);
		hotelService.save(hotel);
		roomRepository.delete(room);
	}

	@Override
	public Room updateRoomInHotel(Long id, RoomRequestDTO roomRequestDTO) {
		Room room = findRoomById(id);
		List<Amenity> amenities = amenityUtil.getAmenities(roomRequestDTO.getAmenityIds());
		room.updateFields(roomRequestDTO, updaterField, amenities);
		return roomRepository.save(room);
	}

	@Override
	public void deleteAllRooms(List<Room> rooms) {
		roomRepository.deleteAll(rooms);
	}
}