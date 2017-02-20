package hr.eestec_zg.frmsbackend.services;


import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.domain.models.Company;

import java.util.List;

public interface TaskService {
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Task task);
    void assignToUser(Long userId, Task task);
    Task getTask(Long id);
    List<Task> getTasksByAssignee(Long userId);
    List<Task> getTasksByEvent(Long eventId);
    List<Task> getTasksByCompany(Long companyId);
    List<Task> getTaskByStatus(TaskStatus status);
}
