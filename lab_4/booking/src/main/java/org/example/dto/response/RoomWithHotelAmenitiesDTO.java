package org.example.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.entity.amenity.Amenity;
import org.example.entity.apartment.Room;

import java.util.List;

@Data
@Builder
public class RoomWithHotelAmenitiesDTO {
	private Room room;
	private List<Amenity> hotelAmenities;
}
