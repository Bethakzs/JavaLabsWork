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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserDTO userDTO;

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

	public Booking(UserDTO userDTO, Apartment apartment, LocalDate startDate, LocalDate endDate, BigDecimal pricePerDay) {
		this.userDTO = userDTO;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pricePerDay = pricePerDay;
		this.days = getDays();
	}

	public int getDays() {
		return (int) (endDate.toEpochDay() - startDate.toEpochDay());
	}

	public BigDecimal getTotalPrice() {
		BigDecimal totalCost = BigDecimal.ZERO;
		LocalDate currentDay = startDate;

		while (!currentDay.isAfter(endDate.minusDays(1))) {
			BigDecimal dayCost = pricePerDay;

			if (currentDay.getMonthValue() == 3 || currentDay.getMonthValue() == 11) {
				dayCost = dayCost.multiply(BigDecimal.valueOf(0.8));
			}

			totalCost = totalCost.add(dayCost);
			currentDay = currentDay.plusDays(1);
		}

		return totalCost;
	}
}

