package org.example.service;

import org.example.dto.request.HouseRequestDTO;
import org.example.entity.apartment.House;

import java.util.List;

public interface HouseService {

	House findHouseById(Long id);

	List<House> getAllHouse();

	House createHouse(HouseRequestDTO houseRequestDTO);

	void deleteHouse(Long id);

	House updateHouse(HouseRequestDTO houseRequestDTO, Long id);
}
