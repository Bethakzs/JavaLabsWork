package org.example.entity.apartment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.entity.amenity.Amenity;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotel extends Apartment {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Room> rooms;

	public Hotel(String name, BigDecimal price, Type type, List<Amenity> amenities, List<Room> rooms) {
		super(name, price, type, amenities);
		this.rooms = rooms;
	}
}
