package lv.savchuk.vehicle.rent.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.vehicle.rent.model.Location;

@Getter
@RequiredArgsConstructor
public enum TripLocation {

	BRNO("Bruno"),
	BUDAPEST("Budapest"),
	PRAGUE("Prague"),
	VIENA("Viena");

	private final String city;

	public Location getLocation() {
		return new Location(getCity());
	}

}
