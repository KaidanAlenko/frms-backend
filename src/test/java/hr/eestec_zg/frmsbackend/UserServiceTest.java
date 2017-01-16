package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.SponsorshipType;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.services.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class UserServiceTest extends TestBase {

    @Autowired
    private UserService userService;

    @Test
    public void testGettingAllTasksForAssignee() {
        // resources
        Company c = new Company("COMPANY", "C", CompanyType.COMPUTING);
        User user = new User("F", "L", "email", "pass", "0000", Role.USER);
        Event event = new Event("E", "E", "2017");
        companyRepository.createCompany(c);
        userRepository.createUser(user);
        eventRepository.createEvent(event);
        Task task = new Task(event, c, user, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);

        // method
        User u = userRepository.getUserByEmail("email");
        List<Task> tasks = userService.getAssignedTasks(u.getId());

        // check
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void testGettingAllTasksForAssigneeFail() {
        // resources
        Company c = new Company("COMPANY", "C", CompanyType.COMPUTING);
        User user = new User("F", "L", "email", "pass", "0000", Role.USER);
        Event event = new Event("E", "E", "2017");
        companyRepository.createCompany(c);
        userRepository.createUser(user);
        eventRepository.createEvent(event);
        Task task = new Task(event, c, null, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);

        // method
        User u = userRepository.getUserByEmail("email");
        List<Task> tasks = userService.getAssignedTasks(u.getId());

        // check
        assertTrue(tasks.size() == 0);
    }

}
