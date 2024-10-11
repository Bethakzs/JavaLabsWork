package org.example.dto.request;

import org.example.entity.apartment.Type;

import java.math.BigDecimal;
import java.util.List;

public interface UpdatableDTO {
	String getName();

	BigDecimal getPrice();

	Type getType();

	List<Long> getAmenityIds();
}

