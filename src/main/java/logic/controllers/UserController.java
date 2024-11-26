package logic.controllers;

import logic.dao.UsersDao;
import logic.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersDao userDao;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        User user = userDao.getUser(id);
        if(user != null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public void createUser(){

    }
    public void updateUser(){}
    public void deleteUser(){}
}
