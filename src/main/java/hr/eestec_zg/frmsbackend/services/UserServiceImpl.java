package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> getAssignedTasks(Long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return taskRepository.getTasksByAssignee(user);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        if(role == null){
            throw new RoleNotDefinedException();
        }
        List <User> allUsersByRole = userRepository.getUsersByRole(role);
        if(allUsersByRole==null){
            throw new UserNotFoundException();
        }
        return allUsersByRole;
    }


    @Override
    public List<User> getUsersByName(String firstName, String lastName) {
        if (firstName==null && lastName==null){
            throw new NameNotDefinedException();
        }
        List <User> usersByName = userRepository.getUsersByName(firstName,lastName);
        if(usersByName==null){
            throw new UserNotFoundException();
        }
        return usersByName;
    }

    @Override
    public List<User> getAllUsers() {
        List <User> allUsers = userRepository.getUsers();
        if(allUsers==null){
            throw new UserNotFoundException();
        }
        return allUsers;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        if(phoneNumber==null){
            throw new PhoneNumberNotDefinedException();
        }
        User userByNumber =userRepository.getUserByPhoneNumber(phoneNumber);
        if(userByNumber==null){
            throw new UserNotFoundException();
        }
        return userByNumber;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email==null){
            throw new EmailNotDefinedException();
        }
        User userByEmail = userRepository.getUserByEmail(email);
        if(userByEmail == null){
            throw new UserNotFoundException();
        }
        return userByEmail;
    }

    @Override
    public User getUserById(Long id) {
        if(id==null){
            throw new IdNotDefinedException();
        }
        User userById = userRepository.getUser(id);
        if(userById == null){
            throw new UserNotFoundException();
        }
        return userById;
    }
}
