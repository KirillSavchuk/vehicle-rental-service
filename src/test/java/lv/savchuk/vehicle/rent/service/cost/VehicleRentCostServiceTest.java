package lv.savchuk.vehicle.rent.service.cost;

import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;
import lv.savchuk.vehicle.rent.exception.VehicleRentCostCalculationException;
import lv.savchuk.vehicle.rent.model.Trip;
import lv.savchuk.vehicle.rent.model.Vehicle;
import lv.savchuk.vehicle.rent.model.VehicleRentOrder;
import lv.savchuk.vehicle.rent.model.VehicleRentRateConfig;
import lv.savchuk.vehicle.rent.service.config.HardcodedVehicleRentRateService;
import lv.savchuk.vehicle.rent.service.config.VehicleRentRateService;
import lv.savchuk.vehicle.rent.service.location.GeoLocationService;
import lv.savchuk.vehicle.rent.service.validator.VehicleRentOrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static lv.savchuk.vehicle.rent.enums.FuelType.DIESEL;
import static lv.savchuk.vehicle.rent.enums.TripLocation.*;
import static lv.savchuk.vehicle.rent.enums.VehicleType.BUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VehicleRentCostServiceTest {

	@Mock
	private VehicleRentOrderValidator rentOrderValidator;
	@Mock
	private VehicleRentRateService rentRateService;
	@Mock
	private GeoLocationService geoLocationService;
	@InjectMocks
	private VehicleRentCostService rentCostService;

	private VehicleRentOrder rentOrder;

	@BeforeEach
	void setUp() throws GeoLocationServiceException {
		final VehicleRentRateConfig rateConfig = new HardcodedVehicleRentRateService().getConfig();

		when(rentRateService.getConfig()).thenReturn(rateConfig);
		when(geoLocationService.getDistanceBetween(any())).thenReturn(100f);

		this.rentOrder = VehicleRentOrder.builder()
			.vehicle(Vehicle.builder()
				.type(BUS)
				.model("SuperBus")
				.fuelType(DIESEL)
				.isAirConditionerEnabled(true)
				.maxPassengerCount(50)
				.build())
			.trip(Trip.path(
				PRAGUE.getLocation(),
				BRNO.getLocation(),
				BUDAPEST.getLocation(),
				PRAGUE.getLocation()))
			.passengerCount(55)
			.build();
	}

	@Test
	void calculateCost() throws VehicleRentCostCalculationException {
		assertThat(rentCostService.calculateCost(rentOrder).floatValue()).isEqualTo(205.80f);
	}

	@Test
	void calculateCost_geoLocationServiceFailed() throws GeoLocationServiceException {
		when(geoLocationService.getDistanceBetween(any())).thenThrow(new GeoLocationServiceException("Error"));

		final VehicleRentCostCalculationException ex = assertThrows(VehicleRentCostCalculationException.class, () -> rentCostService.calculateCost(rentOrder));

		assertThat(ex.getMessage()).contains("Failed to calculate distance between trip cities.");
	}

}