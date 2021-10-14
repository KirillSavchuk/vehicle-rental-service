package lv.savchuk.vehicle.rent.service.validator;

import lv.savchuk.vehicle.rent.exception.TripValidationException;
import lv.savchuk.vehicle.rent.model.Trip;

public class TripValidator implements Validator<Trip> {

	@Override
	public void validate(Trip trip) throws TripValidationException {
		if (trip.getTripPath() == null || trip.getTripPath().size() < 2) {
			throw new TripValidationException("Amount of cities in trip must be equals or larger than 2!");
		}
	}

}
