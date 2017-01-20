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
import hr.eestec_zg.frmsbackend.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class UserServiceTest extends TestBase {

    @Autowired
    private UserService userService;

    private Company c;
    private Event event;
    private User user1,user2,user3,user4;

    @Before
    public void setTestData(){
        c = new Company("COMPANY", "C", CompanyType.COMPUTING);
        event = new Event("E", "E", "2017");
        user1 = new User("F", "L", "email1", "pass1", "0001", Role.USER);
        user2 = new User("F", "A", "email2", "pass2", "0002", Role.COORDINATOR);
        user3 = new User("G", "L", "email3", "pass3", "0003", Role.COORDINATOR);

        companyRepository.createCompany(c);
        eventRepository.createEvent(event);
        userRepository.createUser(user1);
        userRepository.createUser(user2);
        userRepository.createUser(user3);

    }

    @Test
    public void testGettingAllTasksForAssignee() {
        // resources
//        Company c = new Company("COMPANY", "C", CompanyType.COMPUTING);
//        User user = new User("F", "L", "email", "pass", "0000", Role.USER);
//        Event event = new Event("E", "E", "2017");
//        companyRepository.createCompany(c);
//        userRepository.createUser(user1);
//        eventRepository.createEvent(event);
        Task task = new Task(event, c, user1, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);

        // method
        User u = userRepository.getUserByEmail("email1");
        List<Task> tasks = userService.getAssignedTasks(u.getId());

        // check
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void testGettingAllTasksForAssigneeFail() {
        // resources
//        Company c = new Company("COMPANY", "C", CompanyType.COMPUTING);
//        User user = new User("F", "L", "email", "pass", "0000", Role.USER);
//        Event event = new Event("E", "E", "2017");
//        companyRepository.createCompany(c);
//        userRepository.createUser(user1);
//        eventRepository.createEvent(event);
        Task task = new Task(event, c, null, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);

        // method
        User u = userRepository.getUserByEmail("email1");
        List<Task> tasks = userService.getAssignedTasks(u.getId());

        // check
        assertTrue(tasks.size() == 0);
    }


    @Test
    public void testGetAllUsers(){
        UserService userServiceOfEmptyRep = new UserServiceImpl(taskRepository,emptyUserRepository);

        List<User> users = userService.getAllUsers();
        List<User> usersInEmptyRep = userService.getAllUsers();

        assertTrue (users.size()==3 && users.contains(user1) && users.contains(user2) && users.contains(user3));
        assertTrue(usersInEmptyRep.size()==0);
    }

    @Test
    public void testGetUsersByName(){
        List<User> usersByFullName = userService.getUsersByName("F","L");
        List<User> usersByFirstName = userService.getUsersByName("F",null);
        List<User> usersByLastName = userService.getUsersByName(null,"L");
        //List<User> usersByNullName = userRepository.getUsersByName(null,null);

        assertTrue(usersByFullName.size()==1 && usersByFullName.contains(user1));
        assertTrue(usersByFirstName.size()==2 && usersByFirstName.contains(user1) && usersByFirstName.contains(user2));
        assertTrue(usersByLastName.size()==2 && usersByLastName.contains(user1) && usersByLastName.contains(user3));
        //assertTrue(usersByNullName.size()==0);
    }

    @Test
    public void testGetUsersByRole(){
        List<User> usersUser = userService.getUsersByRole(Role.USER);
        List<User> usersCoordinator = userService.getUsersByRole(Role.COORDINATOR);
        List<User> usersAdmin= userService.getUsersByRole(Role.ADMIN);
        //List<User> usersWithNullRole = userRepository.getUsersByRole(null);

        assertTrue(usersUser.size()==1 && usersUser.contains(user1));
        assertTrue(usersCoordinator.size()==2 && usersCoordinator.contains(user2) && usersCoordinator.contains(user3));
        assertTrue(usersAdmin.size()==0);
        //assertTrue(usersWithNullRole.size()==0);
    }

    @Test
    public void testGetUserById(){
        userRepository.createUser(user1);

        User userById = userService.getUserById(user1.getId());

        assertTrue(userById.getId()==user1.getId());

    }

    @Test
    public void testGetUserByEmail(){
        userRepository.createUser(user1);

        User userByEmail = userService.getUserByEmail("email1");

        assertTrue(user1.getEmail()==userByEmail.getEmail());
    }

    @Test
    public void testGetUserByPhoneNumber(){
        userRepository.createUser(user1);

        User userByEmail = userService.getUserByPhoneNumber("0001");

        assertTrue(user1.getPhoneNumber()==userByEmail.getPhoneNumber());

    }
}
