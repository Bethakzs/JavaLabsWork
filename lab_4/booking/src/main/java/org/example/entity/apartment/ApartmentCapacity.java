package org.example.entity.apartment;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.dto.request.UpdatableCapacityDTO;
import org.example.entity.amenity.Amenity;
import org.example.util.UpdaterField;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class ApartmentCapacity extends Apartment {

	protected int maxSpace;

	protected int childrenMaxSpace;

	protected int animalMaxSpace;

	protected ApartmentCapacity(String name, BigDecimal price, Type type, List<Amenity> amenities, int maxSpace) {
		super(name, price, type, amenities);
		this.maxSpace = maxSpace;
		this.childrenMaxSpace = 0;
		this.animalMaxSpace = 0;

		for (Amenity amenity : amenities) {
			if (amenity.getCategory() != null) {
				switch (amenity.getCategory()) {
					case ADULT -> this.maxSpace += amenity.getAdditionalSpace();
					case CHILD -> this.childrenMaxSpace += amenity.getAdditionalSpace();
					case ANIMAL -> this.animalMaxSpace += amenity.getAdditionalSpace();
					default -> throw new IllegalArgumentException("Unknown category: " + amenity.getCategory());
				}
			}
		}
	}

	public <T extends UpdatableCapacityDTO> void updateFields(T requestDTO, UpdaterField updaterField, List<Amenity> amenities) {
		super.updateFields(requestDTO, updaterField, amenities);
		updaterField.updateField(requestDTO.getMaxSpace(), this::setMaxSpace);
	}
}
