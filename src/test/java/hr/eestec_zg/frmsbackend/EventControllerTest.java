package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmscore.domain.models.Company;
import hr.eestec_zg.frmscore.domain.models.CompanyType;
import hr.eestec_zg.frmscore.domain.models.Event;
import hr.eestec_zg.frmscore.domain.models.Role;
import hr.eestec_zg.frmscore.domain.models.SponsorshipType;
import hr.eestec_zg.frmscore.domain.models.Task;
import hr.eestec_zg.frmscore.domain.models.TaskStatus;
import hr.eestec_zg.frmscore.domain.models.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class EventControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(EventControllerTest.class);

    private Event event;

    @Before
    public void setTestData() {
        event = new Event("span", "S", "2017");
        Event event2 = new Event("CroApps", "CA", "2016");
        eventRepository.createEvent(event);
        eventRepository.createEvent(event2);

        User user = new User("Frane", "Varalica", "frane@fer.hr", "frane", "1234", Role.USER, null);
        userRepository.createUser(user);
        Company company = new Company("Infobip", "IB", CompanyType.COMPUTING);
        companyRepository.createCompany(company);
        Task task = new Task(event, company, user, SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");

        taskRepository.createTask(task);

    }

    @Test
    @WithMockUser
    public void testReadSingleEvent() throws Exception {
        final String url = "/events/" + event.getId();
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        Event c = jacksonService.readJson(response.getContentAsString(), Event.class);

        assertEquals("span", c.getName());
        assertEquals("S", c.getShortName());
        assertEquals("2017", c.getYear());
    }

    @Test
    @WithMockUser
    public void testReadSingleEventFail() throws Exception {
        final String url = "/events/" + 77777L;
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testReadEvents() throws Exception {
        final String url = "/events";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        List<Event> c = jacksonService.readListOfObjects(response.getContentAsString(), Event.class);
        assertEquals(2, c.size());

        Event c2 = c.get(1);
        assertEquals(event, c.get(0));
        assertEquals("CroApps", c2.getName());
        assertEquals("CA", c2.getShortName());
        assertEquals("2016", c2.getYear());
    }

    @Test
    @WithMockUser
    public void testDeleteEvent() throws Exception {
        Event event1 = eventRepository.getEventByName("span");
        String url = "/events/" + event1.getId();

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        Event event2 = eventRepository.getEventByName("span");
        assertNull(event2);
    }

    @Test
    @WithMockUser
    public void testDeleteEventFail() throws Exception {
        String url = "/events/" + "727753";

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testCreateReadEvents() throws Exception {
        Event c2 = new Event("spanama", "SS", "2001");
        String url = "/events";
        String c2Json = jacksonService.asJson(c2);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(201, response.getStatus());

        List<Event> c = eventRepository.getEvents();
        assertEquals(3, c.size());
        Event c1 = c.get(2);

        assertEquals("spanama", c1.getName());
        assertEquals("SS", c1.getShortName());
        assertEquals("2001", c1.getYear());
    }

    @Test
    @WithMockUser
    public void testPutReadEvent() throws Exception {
        Event c1 = eventRepository.getEventByName("span");

        c1.setName("spanama");
        c1.setShortName("SS");

        String url = "/events/" + c1.getId();
        String c2Json = jacksonService.asJson(c1);

        logger.debug("Sending PUT request on {}", url);
        MockHttpServletResponse response = put(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        c1 = eventRepository.getEventByName("spanama");

        assertEquals("spanama", c1.getName());
        assertEquals("SS", c1.getShortName());
        assertEquals("2017", c1.getYear());
    }

    @Test
    @WithMockUser
    public void testPutFail() throws Exception {
        Event c2 = new Event("spanama", "SS", "1991");
        String url = "/events/" + 777727L;
        String c2Json = jacksonService.asJson(c2);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = put(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testGetTasksForEvent() throws Exception {
        String url = "/events/" + event.getId() + "/tasks";
        logger.debug("Sending GET request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        List<Task> t = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);

        assertEquals(1,t.size());

        Task task1 = t.get(0);

        Event c = task1.getEvent();
        assertEquals("span", c.getName());
        assertEquals("S", c.getShortName());
        assertEquals("2017", c.getYear());

        User user1 = task1.getAssignee();
        assertEquals("Frane", user1.getFirstName());
        assertEquals("Varalica", user1.getLastName());
    }

    @Test
    @WithMockUser
    public void testGetTasksForEventFail() throws Exception {
        String url = "/events/" + "777727" + "/tasks";
        logger.debug("Sending GET request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testGetUsersForEvent() throws Exception {
        String url = "/events/" + event.getId() + "/users";
        logger.debug("Sending GET request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        List<User> u = jacksonService.readListOfObjects(response.getContentAsString(), User.class);

        assertEquals(1,u.size());

        User user1 = u.get(0);

        assertEquals("Frane", user1.getFirstName());
        assertEquals("Varalica", user1.getLastName());
    }

    @Test
    @WithMockUser
    public void testGetUsersForEventFail() throws Exception {
        String url = "/events/" + "777727L" + "/events";
        logger.debug("Sending GET request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

}