package lv.savchuk.vehicle.rent.service.location;

import lv.savchuk.vehicle.rent.enums.TripLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.String.format;
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
		final NullPointerException exception = assertThrows(NullPointerException.class, callServiceWith(null, null));

		assertThat(exception.getMessage())
			.contains("Service cannot find location by city name: null.");
	}

	@Test
	void getDistanceBetween_validCity_invalidCity() {
		final String fromCity = PRAGUE.getCity();
		final String toCity = "NOT A CITY";

		final NullPointerException exception = assertThrows(NullPointerException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find location by city name: %s.", toCity));
	}

	@Test
	void getDistanceBetween_validCity_validCity_noDistanceFromCity() {
		final String fromCity = VIENA.getCity();
		final String toCity = PRAGUE.getCity();

		final NullPointerException exception = assertThrows(NullPointerException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find distance from '%s' city.", fromCity));
	}

	@Test
	void getDistanceBetween_validCity_validCity_noDistanceToCity() {
		final String fromCity = PRAGUE.getCity();
		final String toCity = VIENA.getCity();

		final NullPointerException exception = assertThrows(NullPointerException.class, callServiceWith(fromCity, toCity));

		assertThat(exception.getMessage())
			.contains(format("Service cannot find distance from '%s' to '%s' city.", fromCity, toCity));
	}

	@MethodSource
	@ParameterizedTest
	void getDistanceBetween(TripLocation locationFrom, TripLocation locationTo, Float expectedDistance) {
		assertThat(service.getDistanceBetween(locationFrom.getCity(), locationTo.getCity())).isEqualTo(expectedDistance);
	}

	static Stream<Arguments> getDistanceBetween() {
		return Stream.of(
			arguments(PRAGUE, BRNO, 200f),
			arguments(PRAGUE, BUDAPEST, 550f),
			arguments(BRNO, VIENA, 150f),
			arguments(BRNO, BUDAPEST, 350f)
		);
	}

	private Executable callServiceWith(String fromCity, String toCity) {
		return () -> service.getDistanceBetween(fromCity, toCity);
	}

}