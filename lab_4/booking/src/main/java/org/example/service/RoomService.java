package org.example.service;

import org.example.dto.request.RoomRequestDTO;
import org.example.dto.response.RoomWithHotelAmenitiesDTO;
import org.example.entity.apartment.Room;

import java.util.List;

public interface RoomService {

	Room findRoomById(Long id);

	RoomWithHotelAmenitiesDTO findRoomWithHotelById(Long roomId);

	List<Room> getAllRooms();

	List<Room> getAllRoomsInHotel(Long hotelId);

	Room addRoomToHotel(RoomRequestDTO roomRequestDTO);

	void deleteRoomFromHotel(Long id);

	void deleteAllRooms(List<Room> rooms);

	Room updateRoomInHotel(Long id, RoomRequestDTO roomRequestDTO);

}
