package lv.savchuk.vehicle.rent.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleRentOrder {

	private final Vehicle vehicle;
	private final Trip trip;
	private final int passengerCount;

}
