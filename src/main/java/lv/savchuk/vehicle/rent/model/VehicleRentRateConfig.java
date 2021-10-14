package lv.savchuk.vehicle.rent.model;

import lombok.Builder;
import lombok.Getter;
import lv.savchuk.vehicle.rent.enums.AdditionalChargeType;
import lv.savchuk.vehicle.rent.enums.FuelType;
import lv.savchuk.vehicle.rent.enums.VehicleType;

import java.util.Map;

@Getter
@Builder
public class VehicleRentRateConfig {

	/**
	 * Standard rate in EURO per KM for {@link FuelType}
	 */
	private final Map<FuelType, Float> fuelTypeRates;

	/**
	 * Discount (-) or Margin (+) in whole percentages (%) applicable for standard rate for specific {@link VehicleType}
	 */
	private final Map<VehicleType, Float> vehicleTypeRateCorrections;

	/**
	 * Additional charges in EURO for specific vehicle rent conditions described in {@link AdditionalChargeType}
	 */
	private final Map<AdditionalChargeType, Float> additionalChargeRates;

}
