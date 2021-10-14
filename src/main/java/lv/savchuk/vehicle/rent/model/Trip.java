package lv.savchuk.vehicle.rent.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

@Getter
public class Trip {

	private final LinkedList<Location> tripPath;

	private Trip(List<Location> tripPath) {
		this.tripPath = new LinkedList<>(tripPath);
	}

	public static Trip roundTrip(Location from, Location to) {
		return path(from, to, from);
	}

	public static Trip path(Location... locations) {
		return path(asList(locations));
	}

	public static Trip path(List<Location> locations) {
		return new Trip(locations);
	}

}
