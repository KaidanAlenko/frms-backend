package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DatabaseBackedTaskRepository extends AbstractRepository<Long, Task> implements TaskRepository {

    private static final String STATUS = "status";
    private static final String ASSIGNEE = "assignee";
    private static final String EVENT = "event";
    private static final String COMPANY = "company";

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
    public Task getTask(Long id) {
        return getByKey(id);
    }

    @Override
    public List<Task> getTasksByAssignee(User user) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.where(cb.equal(root.get(ASSIGNEE).as(User.class), user));
        return getTasks(query.select(root));
    }

    @Override
    public List<Task> getTasksByEvent(Event event) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.where(cb.equal(root.get(EVENT).as(Event.class), event));
        return getTasks(query.select(root));
    }

    @Override
    public List<Task> getTasksByCompany(Company company) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.where(cb.equal(root.get(COMPANY).as(Company.class), company));
        return getTasks(query.select(root));
    }

    @Override
    public List<Task> getTaskByStatus(TaskStatus status) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.where(cb.equal(root.get(STATUS).as(TaskStatus.class), status));
        return getTasks(query.select(root));
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        CriteriaQuery<Task> query = criteriaBuilder().createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        return getTasks(query.select(root))
                .stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    private Task getTask(CriteriaQuery<Task> query) {
        try {
            return getSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<Task> getTasks(CriteriaQuery<Task> query) {
        return getSession().createQuery(query).getResultList();
    }

}
