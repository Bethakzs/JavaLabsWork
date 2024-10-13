package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.HotelRepository;
import org.example.dto.request.HotelRequestDTO;
import org.example.entity.amenity.Amenity;
import org.example.entity.apartment.Hotel;
import org.example.entity.apartment.Room;
import org.example.exception.HotelNotFoundException;
import org.example.service.HotelService;
import org.example.service.RoomService;
import org.example.util.AmenityUtil;
import org.example.util.UpdaterField;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

	private final HotelRepository hotelRepository;
	private final ApplicationContext context;
	private final UpdaterField updaterField;
	private final AmenityUtil amenityUtil;

	@Override
	public Hotel findHotelById(Long id) {
		return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(HttpStatus.NOT_FOUND.value(), "Hotel not found"));
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	@Transactional
	public Hotel createHotel(HotelRequestDTO hotelRequestDTO) {
		List<Amenity> amenities = amenityUtil.getAmenities(hotelRequestDTO.getAmenityIds());
		Hotel hotel = new Hotel(hotelRequestDTO.getName(), hotelRequestDTO.getPrice(), hotelRequestDTO.getType(), amenities, new ArrayList<>());
		return hotelRepository.save(hotel);
	}

	@Override
	@Transactional
	public void deleteHotel(Long id) {
		Hotel hotel = findHotelById(id);
		List<Room> rooms = hotel.getRooms();

		RoomService roomService = context.getBean(RoomService.class);
		roomService.deleteAllRooms(rooms);
		hotelRepository.delete(hotel);

		rooms.forEach(room -> log.info("Rooms for hotel with id {} deleted", room.getId()));
		log.info("Hotel with id {} deleted", id);
	}

	@Override
	public void save(Hotel hotel) {
		hotelRepository.save(hotel);
	}

	@Override
	public Hotel updateHotel(HotelRequestDTO hotelRequestDTO, Long id) {
		Hotel hotel = findHotelById(id);
		List<Amenity> amenities = amenityUtil.getAmenities(hotelRequestDTO.getAmenityIds());
		hotel.updateFields(hotelRequestDTO, updaterField, amenities);
		return hotelRepository.save(hotel);
	}
}