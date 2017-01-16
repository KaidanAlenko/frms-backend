package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.models.Task;

import java.util.List;

public interface UserService {
    List<Task> getAssignedTasks(Long userId);
}
