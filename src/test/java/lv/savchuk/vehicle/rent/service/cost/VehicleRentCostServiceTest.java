package lv.savchuk.vehicle.rent.service.cost;

import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;
import lv.savchuk.vehicle.rent.exception.VehicleRentCostException;
import lv.savchuk.vehicle.rent.model.Trip;
import lv.savchuk.vehicle.rent.model.Vehicle;
import lv.savchuk.vehicle.rent.model.VehicleRentOrder;
import lv.savchuk.vehicle.rent.model.VehicleRentRateConfig;
import lv.savchuk.vehicle.rent.service.config.HardcodedVehicleRentRateService;
import lv.savchuk.vehicle.rent.service.config.VehicleRentRateService;
import lv.savchuk.vehicle.rent.service.location.GeoLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;

import static lv.savchuk.vehicle.rent.enums.FuelType.DIESEL;
import static lv.savchuk.vehicle.rent.enums.TripLocation.*;
import static lv.savchuk.vehicle.rent.enums.VehicleType.BUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleRentCostServiceTest {

	@Mock
	private VehicleRentRateService rentRateService;
	@Mock
	private GeoLocationService geoLocationService;
	@InjectMocks
	private VehicleRentCostService rentCostService;

	private VehicleRentRateConfig rateConfig;

	@BeforeEach
	void setUp() throws GeoLocationServiceException {
		this.rateConfig = new HardcodedVehicleRentRateService().getConfig();

		when(rentRateService.getConfig()).thenReturn(rateConfig);
		when(geoLocationService.getDistanceBetween(any(LinkedList.class))).thenReturn(100f);
	}

	@Test
	void calculateCost() throws GeoLocationServiceException, VehicleRentCostException {
		final VehicleRentOrder rentOrder = VehicleRentOrder.builder()
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

		assertThat(rentCostService.calculateCost(rentOrder).floatValue()).isEqualTo(205.80f);
	}

}