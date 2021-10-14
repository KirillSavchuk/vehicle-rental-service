package lv.savchuk.vehicle.rent.exception;

public class VehicleRentCostCalculationException extends Exception {

	public VehicleRentCostCalculationException(String message) {
		super(message);
	}

	public VehicleRentCostCalculationException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
