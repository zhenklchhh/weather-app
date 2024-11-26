package logic.dao;

import logic.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers(){
        return jdbcTemplate.query("select * from users", new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(long id){
        return jdbcTemplate.query("select * from users where id = ?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findFirst().orElse(null);
    }

    public void saveUser(User user){
        jdbcTemplate.update("insert into users values(?, ?, ?, ?)",
                user.getId(), user.getLogin(), user.getPassword());
    }

    public void updateUser(User updatedUser){
        jdbcTemplate.update("update users set login=?, password=? where id = ?",
                updatedUser.getLogin(), updatedUser.getPassword(), updatedUser.getId());
    }

    public void deleteUser(long id){
        jdbcTemplate.update("delete from users where id = ?", id);
    }
}
