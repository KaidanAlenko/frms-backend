package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.User;

import java.util.List;

public interface UserService {
    List<Task> getAssignedTasks(Long userId);

    List<User> getAllUsers();
    List<User> getUsersByRole(Role role);
    List<User> getUsersByName(String firstName, String lastName);

    User getUserById(Long id);
    User getUserByPhoneNumber(String phoneNumber);
    User getUserByEmail(String email);

}
