package org.example.entity.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.apartment.Apartment;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;

	@Transient
	private UserDTO userId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "apartment_id")
	private Apartment apartment;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Transient  // Calculated field
	private int days;

	private BigDecimal pricePerDay;

	public Booking(UserDTO userId, Apartment apartment, LocalDate startDate, LocalDate endDate, BigDecimal pricePerDay) {
		this.userId = userId;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = getDays();
		this.pricePerDay = pricePerDay;
	}

	public int getDays() {
		return (int) (endDate.toEpochDay() - startDate.toEpochDay());
	}
}

