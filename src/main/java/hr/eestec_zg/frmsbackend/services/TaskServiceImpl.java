package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.EventRepository;
import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.domain.models.dto.TaskDto;
import hr.eestec_zg.frmsbackend.exceptions.CompanyNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.EventNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.TaskNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final EventRepository eventRepository;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository,
                           CompanyRepository companyRepository, EventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Task createTask(TaskDto task) {
        if (task == null) {
            throw new IllegalArgumentException("Task not defined");
        }
        User user = userRepository.getUser(task.getUserId());
        Company company = companyRepository.getCompany(task.getCompanyId());
        if (company == null) {
            throw new IllegalArgumentException("Company does not exist");
        }
        Event event = eventRepository.getEvent(task.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Event does not exist");
        }

        Task taskT = new Task(event, company, user, task.getType(), task.getCallTime(), task.getMailTime(), task.getFollowUpTime(), task.getStatus(), task.getNotes());
        taskRepository.createTask(taskT);
        return taskT;
    }

    @Override
    public void updateTask(Long id, TaskDto task) {
        if (task == null) {
            throw new IllegalArgumentException("Task not defined");
        }

        Task oldTask = this.taskRepository.getTask(id);
        if (oldTask == null) {
            throw new TaskNotFoundException();
        }
        User user = null;
        Long assigneeId = task.getUserId();
        if (assigneeId != null) {
            user = this.userRepository.getUser(assigneeId);
        }
        Company company = this.companyRepository.getCompany(task.getCompanyId());
        if (company == null) {
            throw new CompanyNotFoundException();
        }
        Event event = this.eventRepository.getEvent(task.getEventId());
        if (event == null) {
            throw new EventNotFoundException();
        }

        oldTask.setEvent(event);
        oldTask.setCompany(company);
        oldTask.setAssignee(user);
        oldTask.setType(task.getType());
        oldTask.setCallTime(task.getCallTime());
        oldTask.setMailTime(task.getMailTime());
        oldTask.setFollowUpTime(task.getFollowUpTime());
        oldTask.setStatus(task.getStatus());
        oldTask.setNotes(task.getNotes());

        taskRepository.updateTask(oldTask);

    }

    @Override
    public void deleteTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task not defined");
        }
        taskRepository.deleteTask(task);
    }

    @Override
    public void assignToUser(Long userId, Task task) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId not defined");
        }
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        task.setAssignee(user);
        //
    }

    @Override
    public Task getTask(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id not defined");
        }
        Task task = taskRepository.getTask(id);
        if (task == null) {
            throw new TaskNotFoundException();
        }
        return task;
    }

    @Override
    public List<Task> getTasksByAssignee(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId not defined");
        }
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return taskRepository.getTasksByAssignee(user);
    }

    @Override
    public List<Task> getTasksByEvent(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("EventId not defined");
        }
        Event event = eventRepository.getEvent(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }
        return taskRepository.getTasksByEvent(event);
    }

    @Override
    public List<Task> getTasksByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("CompanyId not defined");
        }
        Company company = companyRepository.getCompany(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }
        return taskRepository.getTasksByCompany(company);
    }

    @Override
    public List<Task> getTaskByStatus(TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status not defined");
        }
        return taskRepository.getTaskByStatus(status);
    }
}
