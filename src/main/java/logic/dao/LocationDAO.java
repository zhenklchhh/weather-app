package logic.dao;

import logic.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Location> getAllLocations() {
        return jdbcTemplate.query("select * from locations", new BeanPropertyRowMapper<>(Location.class));
    }

    public Location getLocationById(long id) {
        return jdbcTemplate.query("select * from locations where id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Location.class)).stream().findFirst().orElse(null);
    }

    public void saveLocation(Location location) {
        jdbcTemplate.update("insert into locations value(?, ?, ?, ?, ?)", location.getId(),
                location.getName(), location.getUserID(), location.getLatitude(), location.getLongitude());
    }

    public void updateLocation(Location location) {
        jdbcTemplate.update("update locations set id=?, name=?, userID=?, latitude=?, longitude=?",
                location.getId(), location.getName(), location.getUserID(), location.getLatitude(),
                location.getLongitude());
    }

    public void deleteLocation(long id) {
        jdbcTemplate.update("delete from locations where id = ?", id);
    }
}
