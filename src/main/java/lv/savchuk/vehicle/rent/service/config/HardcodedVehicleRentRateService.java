package lv.savchuk.vehicle.rent.service.config;

import lv.savchuk.vehicle.rent.enums.AdditionalChargeType;
import lv.savchuk.vehicle.rent.enums.FuelType;
import lv.savchuk.vehicle.rent.enums.VehicleType;
import lv.savchuk.vehicle.rent.model.VehicleRentRateConfig;

import java.util.Map;

import static lv.savchuk.vehicle.rent.enums.FuelType.DIESEL;
import static lv.savchuk.vehicle.rent.enums.FuelType.PETROL;
import static lv.savchuk.vehicle.rent.enums.VehicleType.BUS;
import static lv.savchuk.vehicle.rent.enums.VehicleType.SPORT_CAR;

public class HardcodedVehicleRentRateService implements VehicleRentRateService {

	private final Map<FuelType, Float> DEFAULT_FUEL_TYPE_COST = Map.of(
		PETROL, 1.5f,
		DIESEL, 1.4f
	);

	private final Map<VehicleType, Float> VEHICLE_TYPE_RATE_CORRECTION = Map.of(
		BUS, -2f,
		SPORT_CAR, 5f
	);

	private final Map<AdditionalChargeType, Float> ADDITIONAL_CHARGE_RATES = Map.of(
		AdditionalChargeType.ENABLED_AIR_CONDITIONER, 0.2f,
		AdditionalChargeType.PASSENGER_LIMIT_EXCEEDED, 0.1f
	);

	@Override
	public VehicleRentRateConfig getConfig() {
		return VehicleRentRateConfig.builder()
			.fuelTypeRates(DEFAULT_FUEL_TYPE_COST)
			.vehicleTypeRateCorrections(VEHICLE_TYPE_RATE_CORRECTION)
			.additionalChargeRates(ADDITIONAL_CHARGE_RATES)
			.build();
	}

}
