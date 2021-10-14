package lv.savchuk.vehicle.rent.service.validator;

import lombok.RequiredArgsConstructor;
import lv.savchuk.vehicle.rent.exception.VehicleRentCostCalculationException;
import lv.savchuk.vehicle.rent.model.Trip;
import lv.savchuk.vehicle.rent.model.Vehicle;
import lv.savchuk.vehicle.rent.model.VehicleRentOrder;

@RequiredArgsConstructor
public class VehicleRentOrderValidator implements Validator<VehicleRentOrder> {

	private final Validator<Vehicle> vehicleValidator;
	private final Validator<Trip> tripValidator;

	@Override
	public void validate(VehicleRentOrder vehicleRentOrder) throws VehicleRentCostCalculationException {
		if (vehicleRentOrder.getPassengerCount() <= 0) {
			throw new VehicleRentCostCalculationException("Vehicle passenger count must be a positive natural number!");
		}
		vehicleValidator.validate(vehicleRentOrder.getVehicle());
		tripValidator.validate(vehicleRentOrder.getTrip());
	}

}
