package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.*;
import hr.eestec_zg.frmsbackend.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.GeneratedValue;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private UserService userService;
    private User user1, user2;

    @Before
    public void setTestData() {
        user1 = new User("F", "L", "email1", "pass1", "0001", Role.USER);
        user2 = new User("F", "A", "email2", "pass2", "0002", Role.COORDINATOR);
        userService.createUser(user1);
        userService.createUser(user2);
    }

    @Test
    @WithMockUser
    public void testReadSingleUser() throws Exception {
        final String url = "/users/" + user1.getId();
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        User user = jacksonService.readJson(response.getContentAsString(), User.class);

        assertEquals(user1.getId(), user.getId());
        assertEquals("F", user.getFirstName());
        assertEquals("L", user.getLastName());
        assertEquals("email1", user.getEmail());
    }

    @Test
    @WithMockUser
    public void testReadSingleUserFail() throws Exception {
        final String url = "/users/" + 77777L;
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testReadUsers() throws Exception {
        final String url = "/users";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        List<User> users = jacksonService.readListOfObjects(response.getContentAsString(), User.class);
        assertEquals(2, users.size());
        User u2 = users.get(1);
        assertEquals(user1, users.get(0));
        assertEquals("F", u2.getFirstName());
        assertEquals("A", u2.getLastName());
    }

    @Test
    @WithMockUser
    public void testDeleteReadUser() throws Exception {
        long  userId = userService.getUserByEmail("email1").getId();
        String url = "/users/" + userId;
        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());
        logger.debug("Sending request on {}", url);
        response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());
        logger.debug("Sending request on {}", url);
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }


    @Test
    @WithMockUser
    public void testCreateReadUsers() throws Exception {
        User user3 = new User("Fifo", "Lifo", "email1i@fer.hr", "pass9", "02001", Role.USER);

        String url = "/users";
        String c2Json = jacksonService.asJson(user3);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(201, response.getStatus());

        url = "/users";
        response = get(url);
        List<User> users = jacksonService.readListOfObjects(response.getContentAsString(), User.class);
        assertEquals(3, users.size());
        User u3 = users.get(2);

        assertEquals("Fifo", u3.getFirstName());
        assertEquals("Lifo", u3.getLastName());
        assertEquals("email1i@fer.hr", u3.getEmail());
    }

    @Test
    @WithMockUser
    public void testPutReadCompany() throws Exception {
        User u1 = userService.getUserByEmail("email1");

        String url = "/users/" + u1.getId();
        logger.debug("Sending GET request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        u1.setFirstName("Fico");
        u1.setPhoneNumber("3232323");

        String c2Json = jacksonService.asJson(u1);

        logger.debug("Sending PUT request on {}", url);
        response = put(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        u1 = userService.getUserByEmail("email1");
        logger.debug("Sending GET request on {}", url);
        url = "/users/" + u1.getId();
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        User user = jacksonService.readJson(response.getContentAsString(), User.class);

        assertEquals("Fico", user.getFirstName());
        assertEquals("L", user.getLastName());
        assertEquals("3232323", user.getPhoneNumber());
    }

    @Test
    @WithMockUser
    public void testPutFail() throws Exception {
        User user3 = new User("Fifo", "Lifo", "email1i@fer.hr", "pass9", "02001", Role.USER);
        String url = "/users/" + 777727L;
        String c2Json = jacksonService.asJson(user3);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = put(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());
        url = "/users/" + 777727L;
        logger.debug("Sending GET request on {}", url);
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testGetUsersTasks() throws Exception {
        User u1 = userService.getUserByEmail("email1");
        Event event = new Event("E", "E", "2017");
        eventRepository.createEvent(event);
        Company c = new Company("COMPANY", "C", CompanyType.COMPUTING);
        companyRepository.createCompany(c);
        Task task = new Task(event, c, u1, SponsorshipType.FINANCIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);

        final String url = "/users/" + u1.getId()+"/tasks";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        List<Task> tasks = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);

        assertEquals(1,tasks.size());

    }

    @Test
    @WithMockUser
    public void testGetUsersTasksNull() throws Exception {
        User u1 = userService.getUserByEmail("email1");

        final String url = "/users/" + u1.getId()+"/tasks";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        List<Task> tasks = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);

        assertEquals(0,tasks.size());
    }
}
