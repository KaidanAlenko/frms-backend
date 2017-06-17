package hr.eestec_zg.frmsbackend.controllers;

import hr.eestec_zg.frmscore.domain.dto.TaskStatisticsDto;
import hr.eestec_zg.frmscore.domain.models.Task;
import hr.eestec_zg.frmscore.domain.models.TaskStatus;
import hr.eestec_zg.frmscore.domain.models.User;
import hr.eestec_zg.frmscore.services.TaskService;
import hr.eestec_zg.frmscore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @RequestMapping(value = "/users/{id}/statistics", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TaskStatisticsDto getStatisticsForUser(@PathVariable("id") Long id) {
        return taskService.getStatistics(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return user;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        User oldUser = userService.getUserById(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setRole(user.getRole());

        final String password = user.getPassword();

        if (password != null) {
            oldUser.setPassword(passwordEncoder.encode(password));
        }

        userService.updateUser(oldUser);
    }

    @RequestMapping(value = "/users/{id}/tasks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getUserTasks(@PathVariable("id") Long id, TaskStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        if (status != null) {
            return userService.getAssignedTasks(id, status);
        } else {
            return userService.getAssignedTasks(id);
        }
    }

    @RequestMapping(value = "/users/{id}/notes", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getUserNotes(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        User user = userService.getUserById(id);
        return user.getNotes();
    }

    @RequestMapping(value = "/users/{id}/notes", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateUserNotes(@PathVariable("id") Long id, @RequestBody Map<String, String> requestBody) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        User user = userService.getUserById(id);
        user.setNotes(requestBody.get("notes"));

        userService.updateUser(user);
    }
}
