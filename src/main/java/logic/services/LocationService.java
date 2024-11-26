package logic.services;

import logic.dao.LocationDAO;
import logic.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements LocationServiceInterface {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    @Override
    public void saveLocation(Location location) {
        locationDAO.saveLocation(location);
    }

    @Override
    public Location getLocationById(long id) {
        return locationDAO.getLocationById(id);
    }

    @Override
    public void deleteLocationById(long id) {
        locationDAO.deleteLocation(id);
    }
}
