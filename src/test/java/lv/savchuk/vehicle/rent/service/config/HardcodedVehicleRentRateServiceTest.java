package lv.savchuk.vehicle.rent.service.config;

import lv.savchuk.vehicle.rent.model.VehicleRentRateConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static lv.savchuk.vehicle.rent.enums.AdditionalChargeType.ENABLED_AIR_CONDITIONER;
import static lv.savchuk.vehicle.rent.enums.AdditionalChargeType.PASSENGER_LIMIT_EXCEEDED;
import static lv.savchuk.vehicle.rent.enums.FuelType.DIESEL;
import static lv.savchuk.vehicle.rent.enums.FuelType.PETROL;
import static lv.savchuk.vehicle.rent.enums.VehicleType.BUS;
import static lv.savchuk.vehicle.rent.enums.VehicleType.SPORT_CAR;
import static org.assertj.core.api.Assertions.assertThat;

class HardcodedVehicleRentRateServiceTest {

	private HardcodedVehicleRentRateService service;

	@BeforeEach
	void setUp() {
		this.service = new HardcodedVehicleRentRateService();
	}

	@Test
	void getConfig() {
		final VehicleRentRateConfig config = service.getConfig();

		assertThat(config.getFuelTypeRates()).isNotNull();
		assertThat(config.getFuelTypeRates().get(PETROL)).isEqualTo(1.5f);
		assertThat(config.getFuelTypeRates().get(DIESEL)).isEqualTo(1.4f);

		assertThat(config.getVehicleTypeRateCorrections()).isNotNull();
		assertThat(config.getVehicleTypeRateCorrections().get(BUS)).isEqualTo(-2f);
		assertThat(config.getVehicleTypeRateCorrections().get(SPORT_CAR)).isEqualTo(5f);

		assertThat(config.getAdditionalChargeRates()).isNotNull();
		assertThat(config.getAdditionalChargeRates().get(ENABLED_AIR_CONDITIONER)).isEqualTo(0.2f);
		assertThat(config.getAdditionalChargeRates().get(PASSENGER_LIMIT_EXCEEDED)).isEqualTo(0.1f);
	}

}