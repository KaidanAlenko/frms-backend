package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.SponsorshipType;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.domain.models.dto.TaskDto;
import hr.eestec_zg.frmsbackend.exceptions.TaskNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.UserNotFoundException;
import hr.eestec_zg.frmsbackend.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaskServiceTest extends TestBase{

    @Autowired
    private TaskService taskService;
    private Task t1, t2;
    private Event event;
    private User user, user2;
    private Company company;

    @Before
    public void setTestData(){
        event = new Event("E", "E", "2017");
        user = new User("F", "L", "email1", "pass1", "0001", Role.USER);
        company = new Company("COMPANY", "C", CompanyType.COMPUTING);
        companyRepository.createCompany(company);
        eventRepository.createEvent(event);
        userRepository.createUser(user);
        t1 = new Task(event, company, user, SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(t1);
    }

    @Test
    public void testCreateTask(){
        user2 = new User("Fico", "Ls", "emaail1", "psass1", "0001", Role.USER);
        userRepository.createUser(user2);
        TaskDto task = new TaskDto(event.getId(), company.getId(), user2.getId(), SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        Task newTask = taskService.createTask(task);
        List<Task> tasks = taskService.getTasksByCompany(company.getId());
        assertTrue(tasks.size() == 2 && tasks.contains(newTask));
    }

    @Test
    public void testCreateDeleteTask(){
        user2 = new User("Ficasdo", "Lfs", "emagail1", "psagss1", "0001", Role.USER);
        userRepository.createUser(user2);
        TaskDto task = new TaskDto(event.getId(), company.getId(), user2.getId(), SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        Task newTask = taskService.createTask(task);
        taskService.deleteTask(t1);
        List<Task> tasks = taskService.getTasksByCompany(company.getId());
        assertTrue(tasks.size() == 1 && tasks.contains(newTask));
    }

    @Test
    public void testGetUpdateTask() {
        user2 = new User("Ficasdo", "Lfs", "emagail1", "psagss1", "0001", Role.USER);
        userRepository.createUser(user2);
        Task task = taskService.getTask(t1.getId());
        task.setAssignee(user2);

        TaskDto taskDto = new TaskDto(
                task.getEvent().getId(),
                task.getCompany().getId(),
                task.getAssignee().getId(),
                task.getType(),
                task.getCallTime(),
                task.getMailTime(),
                task.getFollowUpTime(),
                task.getStatus(),
                task.getNotes());

        taskService.updateTask(task.getId(), taskDto);

        List<Task> tasks = taskService.getTasksByCompany(company.getId());
        assertTrue(tasks.size() == 1 && tasks.contains(task));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExceptionCreateTask(){
        taskService.createTask(null);
    }

    @Test(expected = TaskNotFoundException.class)
    public void testTaskNotFoundExceptionGetTask(){

        taskService.getTask(7777L);
    }

    @Test(expected = UserNotFoundException.class)
    public void testAssigneeNotFoundException(){
        taskService.assignToUser(7777L,t1);
    }

    @Test
    public void testAssignToUser(){
        t2 = new Task(event, company, null, SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
    taskService.assignToUser(user.getId(),t2);
    assertNotNull(t2.getAssignee());
    }

    @Test
    public void testGetTasksByCompany(){
        List<Task> tasks = taskService.getTasksByCompany(company.getId());
        assertEquals(1, tasks.size());
     }

    @Test
    public void testGetTasksByEvent(){
        List<Task> tasks = taskService.getTasksByEvent(event.getId());
        assertEquals(1, tasks.size());
    }
}
