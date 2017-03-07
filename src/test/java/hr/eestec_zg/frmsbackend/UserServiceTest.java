package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.*;
import hr.eestec_zg.frmsbackend.exceptions.UserNotFoundException;
import hr.eestec_zg.frmsbackend.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Task task = new Task(event, c, user1, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);
        // method
        User u = userRepository.getUserByEmail("email1");
        List<Task> tasks = userService.getAssignedTasks(u.getId());
        // check
        assertEquals(1,tasks.size());
    }

    @Test
    public void testGettingAllTasksForAssigneeFail() {
        // resources
        Task task = new Task(event, c, null, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);
        // method
        User u = userRepository.getUserByEmail("email1");
        List<Task> tasks = userService.getAssignedTasks(u.getId());
        // check
        assertEquals(0, tasks.size());
    }


    @Test
    public void testGetAllUsers(){
        List<User> users = userService.getAllUsers();
        assertTrue (users.size()==3 && users.contains(user1) && users.contains(user2) && users.contains(user3));
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
        assertTrue(user1.getEmail().equals(userByEmail.getEmail()));
    }

    @Test
    public void testGetUserByPhoneNumber(){
        userRepository.createUser(user1);
        User userByEmail = userService.getUserByPhoneNumber("0001");
        assertTrue(user1.getPhoneNumber().equals(userByEmail.getPhoneNumber()));

    }

    @Test
    public void testCreateUser(){
        user1 = new User("Fafa", "Fufa", "em11aial1@ss", "pass1", "0001", Role.USER);
        userService.createUser(user1);
        assertEquals("em11aial1@ss",userService.getUserByEmail("em11aial1@ss").getEmail());
    }

    @Test
    public void testUpdateUser(){
     user1 = userService.getUserByEmail("email1");
     user1.setEmail("asdasdasdasd@haha");
     userService.updateUser(user1);
     assertEquals("asdasdasdasd@haha",userService.getUserByEmail("asdasdasdasd@haha").getEmail());
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserFail(){

        userService.deleteUser(67586755L);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateUserFail(){

        user1 = userService.getUserById(67586755L);
        user1.setFirstName("asdasd");
        userService.updateUser(user1);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteUser(){
      userService.deleteUser(userService.getUserByEmail("email1").getId());
      assertEquals(null,userService.getUserByEmail("asdasdasdasd@haha").getEmail());
    }

    @Test
    public void testChangePassUser(){
        user1 = userService.getUserByEmail("email1");
        userService.changePassword(user1.getId(),"pass1","blabla");
        user2 = userService.getUserByEmail("email1");
        assertEquals("blabla",user2.getPassword());
    }
}
