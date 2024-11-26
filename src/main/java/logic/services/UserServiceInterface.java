package logic.services;

import logic.models.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(long id);
    void deleteUserById(long id);
}
