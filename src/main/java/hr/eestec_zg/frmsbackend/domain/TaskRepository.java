package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Task;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface TaskRepository {
    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    Task getTask(CriteriaQuery<Task> criteria);

    List<Task> getTasks(CriteriaQuery<Task> criteria);
}
