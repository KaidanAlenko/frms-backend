package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;

import java.util.List;
import java.util.function.Predicate;

public interface TaskRepository {
    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    Task getTask(Long id);

    List<Task> getTasksByAssignee(User user);

    List<Task> getTasksByEvent(Event event);

    List<Task> getTasksByCompany(Company company);

    List<Task> getTaskByStatus(TaskStatus status);

    List<Task> getTasks(Predicate<Task> condition);

    default List<Task> getTasks() {
        return getTasks(t -> true);
    }
}
