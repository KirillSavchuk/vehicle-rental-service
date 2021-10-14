package lv.savchuk.vehicle.rent.service.cost;

import lombok.RequiredArgsConstructor;
import lv.savchuk.vehicle.rent.enums.AdditionalChargeType;
import lv.savchuk.vehicle.rent.exception.GeoLocationServiceException;
import lv.savchuk.vehicle.rent.exception.VehicleRentCostException;
import lv.savchuk.vehicle.rent.model.*;
import lv.savchuk.vehicle.rent.service.config.VehicleRentRateService;
import lv.savchuk.vehicle.rent.service.location.GeoLocationService;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;
import static lv.savchuk.vehicle.rent.enums.AdditionalChargeType.ENABLED_AIR_CONDITIONER;
import static lv.savchuk.vehicle.rent.enums.AdditionalChargeType.EXTRA_PASSENGER;

@RequiredArgsConstructor
public class VehicleRentCostService {

	private final VehicleRentRateService rentRateService;
	private final GeoLocationService geoLocationService;

	public Float calculateCost(VehicleRentOrder order) throws GeoLocationServiceException, VehicleRentCostException {
		final VehicleRentRateConfig rateConfig = rentRateService.getConfig();
		final float totalCostPerKM = calculateCostPerKM(rateConfig, order.getVehicle(), order.getPassengerCount());
		final float totalTripDistance = getTotalTripDistance(order.getTrip());
		return totalTripDistance * totalCostPerKM;
	}

	private Float calculateCostPerKM(VehicleRentRateConfig rateConfig, Vehicle vehicle, int passengerCount) throws VehicleRentCostException {
		final float fuelRate = getFuelRate(rateConfig, vehicle);
		final float vehicleRateCorrection = getVehicleRateCorrection(rateConfig, vehicle);
		final float extraPassengerRate = getExceedPassengersRate(rateConfig, vehicle, passengerCount);
		final float airConditionerRate = getEnabledAirConditionerRate(rateConfig, vehicle);
		final float costPerKm = fuelRate + extraPassengerRate + airConditionerRate;
		return costPerKm + costPerKm * (vehicleRateCorrection / 100);
	}

	private float getFuelRate(VehicleRentRateConfig rateConfig, Vehicle vehicle) throws VehicleRentCostException {
		return getRateConfigValue(rateConfig.getFuelTypeRates(), vehicle.getFuelType());
	}

	private float getVehicleRateCorrection(VehicleRentRateConfig rateConfig, Vehicle vehicle) throws VehicleRentCostException {
		return getRateConfigValue(rateConfig.getVehicleTypeRateCorrections(), vehicle.getType());
	}

	private float getExceedPassengersRate(VehicleRentRateConfig rateConfig, Vehicle vehicle, int passengerCount) throws VehicleRentCostException {
		if (vehicle.getMaxPassengerCount() >= passengerCount) {
			return 0;
		}
		final int extraPassengerCount = passengerCount - vehicle.getMaxPassengerCount();
		final float extraPassengerRate = getAdditionalChargeRate(rateConfig, EXTRA_PASSENGER);
		return extraPassengerCount * extraPassengerRate;
	}

	private float getEnabledAirConditionerRate(VehicleRentRateConfig rateConfig, Vehicle vehicle) throws VehicleRentCostException {
		return vehicle.isAirConditionerEnabled() ? getAdditionalChargeRate(rateConfig, ENABLED_AIR_CONDITIONER) : 0;
	}

	private float getAdditionalChargeRate(VehicleRentRateConfig rateConfig, AdditionalChargeType type) throws VehicleRentCostException {
		return getRateConfigValue(rateConfig.getAdditionalChargeRates(), type);
	}

	private <T, R> R getRateConfigValue(Map<T, R> rateMap, T rateType) throws VehicleRentCostException {
		return Optional.ofNullable(rateMap.get(rateType))
			.orElseThrow(() -> new VehicleRentCostException(format("Cannot find '%s' rate config value.", rateType.getClass().getSimpleName())));
	}

	private float getTotalTripDistance(Trip trip) throws GeoLocationServiceException {
		final LinkedList<String> cityPath = trip.getTripPath()
			.stream()
			.map(Location::getCity)
			.collect(toCollection(LinkedList::new));
		return geoLocationService.getDistanceBetween(cityPath);
	}

}
