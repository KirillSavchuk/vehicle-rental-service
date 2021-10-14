package lv.savchuk.vehicle.rent.service.location;

import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;

import java.util.LinkedList;

public interface GeoLocationService {

	Float getDistanceBetween(String fromCity, String toCity) throws GeoLocationServiceException;

	default Float getDistanceBetween(LinkedList<String> citiesPath) throws GeoLocationServiceException {
		if (citiesPath == null || citiesPath.size() < 2) {
			throw new GeoLocationServiceException("Not enough cities provided! Distance between cities can be calculated for 2 or more cities.");
		}

		float totalTripDistance = 0f;
		String fromCity = citiesPath.pop();
		while (!citiesPath.isEmpty()) {
			String toCity = citiesPath.pop();
			totalTripDistance += getDistanceBetween(fromCity, toCity);
			fromCity = toCity;
		}

		return totalTripDistance;
	}

}
