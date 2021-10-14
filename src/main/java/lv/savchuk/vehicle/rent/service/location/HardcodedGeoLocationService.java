package lv.savchuk.vehicle.rent.service.location;

import lv.savchuk.vehicle.rent.enums.TripLocation;
import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static lv.savchuk.vehicle.rent.enums.TripLocation.*;

public class HardcodedGeoLocationService implements GeoLocationService {

	private static final Map<String, TripLocation> CITY_TO_TRIP_LOCATION_MAP = initCityToTripLocationMap();

	private static Map<String, TripLocation> initCityToTripLocationMap() {
		return Arrays.stream(TripLocation.values()).collect(toMap(TripLocation::getCity, identity()));
	}

	// In the real life distance between 2 points are not equal bidirectionally
	// because distance "from -> to" can be longer due to closed road due to roadworks
	private static final Map<TripLocation, Map<TripLocation, Float>> DISTANCE_BETWEEN_MAP = Map.copyOf(new HashMap<>() {{
		put(PRAGUE, Map.of(
			BRNO, 200f,
			BUDAPEST, 550f
		));
		put(BRNO, Map.of(
			VIENA, 150f,
			BUDAPEST, 350f
		));
		put(BUDAPEST, Map.of(
			PRAGUE, 550f
		));
	}});


	@Override
	public Float getDistanceBetween(String fromCity, String toCity) throws GeoLocationServiceException {
		final TripLocation from = CITY_TO_TRIP_LOCATION_MAP.get(fromCity);
		throwIfNull(from, format("Service cannot find location by city name: %s.", fromCity));

		final TripLocation to = CITY_TO_TRIP_LOCATION_MAP.get(toCity);
		throwIfNull(to, format("Service cannot find location by city name: %s.", toCity));

		final Map<TripLocation, Float> sourceCityMap = DISTANCE_BETWEEN_MAP.get(from);
		throwIfNull(sourceCityMap, format("Service cannot find distance from '%s' city.", fromCity));

		final Float distanceBetweenCities = sourceCityMap.get(to);
		throwIfNull(distanceBetweenCities, format("Service cannot find distance from '%s' to '%s' city.", fromCity, toCity));

		return distanceBetweenCities;
	}

	private void throwIfNull(Object nullCheckObject, String exceptionMessage) throws GeoLocationServiceException {
		if (nullCheckObject == null) {
			throw new GeoLocationServiceException(exceptionMessage);
		}
	}

}
