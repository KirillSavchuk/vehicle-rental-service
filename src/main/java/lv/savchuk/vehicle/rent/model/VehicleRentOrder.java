package lv.savchuk.vehicle.rent.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VehicleRentOrder {

	private final Vehicle vehicle;
	private final Trip trip;
	private final int passengerCount;

}
