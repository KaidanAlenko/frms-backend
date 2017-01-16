package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.exceptions.UserNotFoundException;
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

}
