package lv.savchuk.vehicle.rent.service.validator;

import lv.savchuk.vehicle.rent.exception.VehicleValidationException;
import lv.savchuk.vehicle.rent.model.Vehicle;

public class VehicleValidator implements Validator<Vehicle> {

	@Override
	public void validate(Vehicle vehicle) throws VehicleValidationException {
		if (vehicle.getMaxPassengerCount() <= 0) {
			throw new VehicleValidationException("Vehicle max passenger count must be a positive natural number!");
		}
	}

}
