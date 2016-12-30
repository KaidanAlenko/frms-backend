package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class DatabaseBackedTaskRepository extends AbstractRepository<Long, Task> implements TaskRepository {

    @Override
    public void createTask(Task task) {
        persist(task);
    }

    @Override
    public void updateTask(Task task) {
        update(task);
    }

    @Override
    public void deleteTask(Task task) {
        delete(task);
    }

    @Override
    public Task getTask(CriteriaQuery<Task> criteria) {
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Task> getTasks(CriteriaQuery<Task> criteria) {
        return getSession().createQuery(criteria).getResultList();
    }
}
