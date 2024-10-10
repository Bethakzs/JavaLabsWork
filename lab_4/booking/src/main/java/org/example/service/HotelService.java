package org.example.service;

import org.example.dto.request.HotelRequestDTO;
import org.example.entity.apartment.Hotel;

import java.util.List;

public interface HotelService {

	Hotel findHotelById(Long id);

	List<Hotel> getAllHotels();

	Hotel createHotel(HotelRequestDTO hotelRequestDTO);

	void deleteHotel(Long id);

	Hotel updateHotel(HotelRequestDTO hotelRequestDTO, Long id);

	void save(Hotel hotel);
}
