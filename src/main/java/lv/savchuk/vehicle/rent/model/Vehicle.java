package lv.savchuk.vehicle.rent.model;

import lombok.Getter;
import lv.savchuk.vehicle.rent.enums.FuelType;
import lv.savchuk.vehicle.rent.enums.VehicleType;

@Getter
public abstract class Vehicle {

	protected VehicleType type;
	protected String model;
	protected FuelType fuelType;
	protected boolean hasAirConditioner;
	protected int maxPassengerCount;

}
