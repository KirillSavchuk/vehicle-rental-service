package lv.savchuk.vehicle.rent.service.location;

import lv.savchuk.vehicle.rent.enums.TripLocation;
import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toCollection;
import static lv.savchuk.vehicle.rent.enums.TripLocation.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HardcodedGeoLocationServiceTest {

	private HardcodedGeoLocationService service;

	@BeforeEach
	void setUp() {
		this.service = new HardcodedGeoLocationService();
	}

	@Test
	void getDistanceBetween_nullCity_nullCity() {
		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(null, null));

		assertThat(exception.getMessage())
			.contains("Service cannot find location by city name: null.");
	}

	@Test
	void getDistanceBetween_validCity_invalidCity() {
		final String fromCity = PRAGUE.getCity();
		final String toCity = "NOT A CITY";

		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find location by city name: %s.", toCity));
	}

	@Test
	void getDistanceBetween_validCity_validCity_noDistanceFromCity() {
		final String fromCity = VIENA.getCity();
		final String toCity = PRAGUE.getCity();

		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find distance from '%s' city.", fromCity));
	}

	@Test
	void getDistanceBetween_validCity_validCity_noDistanceToCity() {
		final String fromCity = PRAGUE.getCity();
		final String toCity = VIENA.getCity();

		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find distance from '%s' to '%s' city.", fromCity, toCity));
	}

	@MethodSource
	@ParameterizedTest
	void getDistanceBetween_fromCity_toCity(TripLocation locationFrom, TripLocation locationTo, Float expectedDistance) throws GeoLocationServiceException {
		assertThat(service.getDistanceBetween(locationFrom.getCity(), locationTo.getCity())).isEqualTo(expectedDistance);
	}

	static Stream<Arguments> getDistanceBetween_fromCity_toCity() {
		return Stream.of(
			arguments(PRAGUE, BRNO, 200f),
			arguments(PRAGUE, BUDAPEST, 550f),
			arguments(BRNO, VIENA, 150f),
			arguments(BRNO, BUDAPEST, 350f)
		);
	}

	@Test
	void getDistanceBetween_nullCityPath() {
		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(null));

		assertThat(exception.getMessage())
			.contains("Not enough cities provided! Distance between cities can be calculated for 2 or more cities.");
	}

	@Test
	void getDistanceBetween_oneCityPath() {
		final LinkedList<String> citiesPath = new LinkedList<>(singletonList(PRAGUE.getCity()));

		final GeoLocationServiceException exception = assertThrows(GeoLocationServiceException.class, callServiceWith(citiesPath));

		assertThat(exception.getMessage())
			.contains("Not enough cities provided! Distance between cities can be calculated for 2 or more cities.");
	}

	@MethodSource
	@ParameterizedTest
	void getDistanceBetween_citiesPath(LinkedList<String> citiesPath, Float expectedTotalDistance) throws GeoLocationServiceException {
		assertThat(service.getDistanceBetween(citiesPath)).isEqualTo(expectedTotalDistance);
	}

	static Stream<Arguments> getDistanceBetween_citiesPath() {
		return Stream.of(
			arguments(getCitiesPath(PRAGUE, BRNO), 200f),
			arguments(getCitiesPath(PRAGUE, BRNO, BUDAPEST, PRAGUE), 1100f)
		);
	}

	private static LinkedList<String> getCitiesPath(TripLocation... locations) {
		return Arrays.stream(locations).map(TripLocation::getCity).collect(toCollection(LinkedList::new));
	}

	private Executable callServiceWith(String fromCity, String toCity) {
		return () -> service.getDistanceBetween(fromCity, toCity);
	}

	private Executable callServiceWith(LinkedList<String> citiesPath) {
		return () -> service.getDistanceBetween(citiesPath);
	}

}