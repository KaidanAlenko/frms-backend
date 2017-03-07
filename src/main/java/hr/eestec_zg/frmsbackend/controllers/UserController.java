package hr.eestec_zg.frmsbackend.controllers;

import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

     @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void putUser(@PathVariable("id") Long id, @RequestBody User user) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        userService.updateUser(user);
    }
    @RequestMapping(value = "/users/{id}/tasks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getUserTasks(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        return userService.getAssignedTasks(id);
    }
}
