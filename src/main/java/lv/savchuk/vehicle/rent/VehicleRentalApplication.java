package lv.savchuk.vehicle.rent;

import lv.savchuk.vehicle.rent.exception.VehicleRentCostCalculationException;
import lv.savchuk.vehicle.rent.model.Trip;
import lv.savchuk.vehicle.rent.model.Vehicle;
import lv.savchuk.vehicle.rent.model.VehicleRentOrder;
import lv.savchuk.vehicle.rent.service.config.HardcodedVehicleRentRateService;
import lv.savchuk.vehicle.rent.service.cost.VehicleRentCostService;
import lv.savchuk.vehicle.rent.service.location.HardcodedGeoLocationService;
import lv.savchuk.vehicle.rent.service.validator.TripValidator;
import lv.savchuk.vehicle.rent.service.validator.VehicleRentOrderValidator;
import lv.savchuk.vehicle.rent.service.validator.VehicleValidator;

import java.math.BigDecimal;

import static lv.savchuk.vehicle.rent.enums.FuelType.DIESEL;
import static lv.savchuk.vehicle.rent.enums.TripLocation.*;
import static lv.savchuk.vehicle.rent.enums.VehicleType.BUS;

public class VehicleRentalApplication {

	public static void main(String[] args) throws VehicleRentCostCalculationException {
		final VehicleRentalApplication app = new VehicleRentalApplication();
		System.out.println("Hello Vehicle Rental World!");
		System.out.println("Example vehicle rental cost: " + app.calculateExampleCost().toString());
	}

	private BigDecimal calculateExampleCost() throws VehicleRentCostCalculationException {
		final VehicleRentCostService vehicleRentCostService = initService();
		final VehicleRentOrder vehicleRentOrder = getVehicleRentOrder();
		return vehicleRentCostService.calculateCost(vehicleRentOrder);
	}

	private VehicleRentCostService initService() {
		return new VehicleRentCostService(
			new VehicleRentOrderValidator(
				new VehicleValidator(),
				new TripValidator()
			),
			new HardcodedVehicleRentRateService(),
			new HardcodedGeoLocationService()
		);
	}

	private VehicleRentOrder getVehicleRentOrder() {
		return VehicleRentOrder.builder()
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

}