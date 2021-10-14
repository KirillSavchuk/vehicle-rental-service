package lv.savchuk.vehicle.rent.service.cost;

import lombok.RequiredArgsConstructor;
import lv.savchuk.vehicle.rent.model.VehicleRentOrder;
import lv.savchuk.vehicle.rent.service.config.VehicleRentRateService;
import lv.savchuk.vehicle.rent.service.location.GeoLocationService;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class VehicleRentCostService {

	private final VehicleRentRateService rentRateService;
	private final GeoLocationService geoLocationService;

	public BigDecimal calculateCost(VehicleRentOrder order) {
		return null;
	}

}
