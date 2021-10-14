package lv.savchuk.vehicle.rent.service.location;

public interface GeoLocationService {

	Float getDistanceBetween(String fromCity, String toCity);

}
