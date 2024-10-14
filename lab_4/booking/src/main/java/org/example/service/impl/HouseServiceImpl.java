package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.HouseRepository;
import org.example.dto.request.HouseRequestDTO;
import org.example.entity.amenity.Amenity;
import org.example.entity.apartment.House;
import org.example.exception.ApartmentIsBookedException;
import org.example.exception.HouseNotFoundException;
import org.example.service.HouseService;
import org.example.util.AmenityUtil;
import org.example.util.UpdaterField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseServiceImpl implements HouseService {

	private final HouseRepository houseRepository;
	private final UpdaterField updaterField;
	private final AmenityUtil amenityUtil;

	@Override
	public House findHouseById(Long id) {
		return houseRepository.findById(id)
				.orElseThrow(() -> new HouseNotFoundException(HttpStatus.NOT_FOUND.value(), "House not found"));
	}

	@Override
	public List<House> getAllHouse() {
		return houseRepository.findAll();
	}

	@Override
	@Transactional
	public House createHouse(HouseRequestDTO houseRequestDTO) {
		List<Amenity> amenities = amenityUtil.getAmenities(houseRequestDTO.getAmenityIds());
		House house = new House(houseRequestDTO.getName(), houseRequestDTO.getPrice(),
				houseRequestDTO.getType(), amenities, houseRequestDTO.getMaxSpace());
		return houseRepository.save(house);
	}

	@Override
	public void deleteHouse(Long id) {
		House house = findHouseById(id);

		if (amenityUtil.existsByApartment(house)) {
			throw new ApartmentIsBookedException(HttpStatus.BAD_REQUEST.value(), "Cannot delete house because it is linked to a booking.");
		}

		houseRepository.delete(house);
	}

	@Override
	public House updateHouse(HouseRequestDTO houseRequestDTO, Long id) {
		House house = findHouseById(id);
		List<Amenity> amenities = amenityUtil.getAmenities(houseRequestDTO.getAmenityIds());
		house.updateFields(houseRequestDTO, updaterField, amenities);
		return houseRepository.save(house);
	}
}