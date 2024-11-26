package logic.services;

import logic.models.Location;

import java.util.List;

public interface LocationServiceInterface {
    List<Location> getAllLocations();
    void saveLocation(Location location);
    Location getLocationById(long id);
    void deleteLocationById(long id);
}
