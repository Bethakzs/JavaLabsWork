package org.example.entity.amenity;

import lombok.Getter;

@Getter
public enum AmenityType {
	WI_FI(5),
	KITCHEN(20),
	BATHROOM(15),
	SECOND_BATHROOM(10),
	SPA(50),
	FOOTBALL_STADIUM(100),
	SWIMMING_POOL(70),
	SOFA(10),
	DOUBLE_SOFA(15),
	CHILD_BED(5),
	DOG_HOUSE(3);

	private final int value;

	AmenityType(int value) {
		this.value = value;
	}

}
