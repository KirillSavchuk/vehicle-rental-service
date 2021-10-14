package lv.savchuk.vehicle.rent.service.validator;

import lv.savchuk.vehicle.rent.exception.VehicleRentCostCalculationException;

public interface Validator<T> {

	void validate(T t) throws VehicleRentCostCalculationException;

}
