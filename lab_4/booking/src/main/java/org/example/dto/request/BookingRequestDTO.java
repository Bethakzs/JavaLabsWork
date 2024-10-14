package org.example.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

	@NotNull(message = "Apartment ID must not be null")
	private Long apartmentId;

	@NotNull(message = "Start date must not be null")
	@FutureOrPresent(message = "Start date must be today or in the future")
	private LocalDate startDate;

	@NotNull(message = "End date must not be null")
	@FutureOrPresent(message = "End date must be today or in the future")
	private LocalDate endDate;

}

