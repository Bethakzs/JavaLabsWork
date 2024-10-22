package org.example.entity.apartment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.request.UpdatableDTO;
import org.example.entity.amenity.Amenity;
import org.example.util.UpdaterField;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "apartments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Apartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Type type;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "apartment_amenities",
			joinColumns = @JoinColumn(name = "apartment_id"),
			inverseJoinColumns = @JoinColumn(name = "amenity_id")
	)
	private List<Amenity> amenities;

	protected Apartment(String name, BigDecimal price, Type type, List<Amenity> amenities) {
		this.name = name;
		this.price = price;
		this.type = type;
		this.amenities = amenities;
	}

	public <T extends UpdatableDTO> void updateFields(T requestDTO, UpdaterField updaterField, List<Amenity> amenities) {
		updaterField.updateField(requestDTO.getName(), this::setName);
		updaterField.updateField(requestDTO.getPrice(), this::setPrice);
		updaterField.updateField(requestDTO.getType(), this::setType);
		this.setAmenities(amenities);
	}
}
