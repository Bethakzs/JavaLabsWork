package org.example.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.entity.amenity.AmenityType;
import org.example.entity.amenity.Category;

@Data
@Builder
public class AmenityRequestDTO {

	@NotNull(message = "Amenity type cannot be null")
	private AmenityType amenityType;
	private int additionalSpace;
	private Category category;
}