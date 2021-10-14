package lv.savchuk.vehicle.rent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.savchuk.vehicle.rent.enums.FuelType;
import lv.savchuk.vehicle.rent.enums.VehicleType;

@Getter
@Builder
@RequiredArgsConstructor
public class Vehicle {

	private final VehicleType type;
	private final String model;
	private final FuelType fuelType;
	private final boolean isAirConditionerEnabled;
	private final int maxPassengerCount;

}
