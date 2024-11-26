package logic.services;

import logic.dao.UserDAO;
import logic.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public User getUserById(long id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found for id : " + id);
        }
        return user;
    }

    @Override
    public void deleteUserById(long id) {
        userDAO.deleteUser(id);
    }
}
