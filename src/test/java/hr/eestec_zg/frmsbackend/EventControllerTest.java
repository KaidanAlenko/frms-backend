package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.User;
import hr.eestec_zg.frmsbackend.services.EventService;
import hr.eestec_zg.frmsbackend.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class EventControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(EventControllerTest.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private TaskService taskService;
    private Event event;
    private Task task;
    private User user;

    @Before
    public void setTestData() {
        event = new Event("span", "S", "2017");
        Event event2 = new Event("CroApps", "CA", "2016");
        eventService.createEvent(event);
        eventService.createEvent(event2);

        user = new User("Frane", "Varalica", "frane@fer.hr", "frane", "1234", Role.USER);

        task = new Task();
        task.setEvent(event);
        task.setAssignee(user);
        taskService.createTask(task);

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
    public void testDeleteReadEvents() throws Exception {
        long  eventId = eventService.getEventByName("span").getId();
        String url = "/events/" + eventId;
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
    public void testCreateReadEvent() throws Exception {
        Event c2 = new Event("spanama", "SS", "2001");
        String url = "/events";
        String c2Json = jacksonService.asJson(c2);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(201, response.getStatus());

        url = "/events";
        response = get(url);
        List<Event> c = jacksonService.readListOfObjects(response.getContentAsString(), Event.class);
        assertEquals(3, c.size());
        Event c1 = c.get(2);

        assertEquals("spanama", c1.getName());
        assertEquals("SS", c1.getShortName());
        assertEquals("2001", c1.getYear());
    }

    @Test
    @WithMockUser
    public void testPutReadEvent() throws Exception {
        Event c1 = eventService.getEventByName("span");

        String url = "/events/" + c1.getId();
        logger.debug("Sending GET request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        c1.setName("spanama");
        c1.setShortName("SS");

        String c2Json = jacksonService.asJson(c1);

        logger.debug("Sending PUT request on {}", url);
        response = put(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        c1 = eventService.getEventByName("spanama");
        logger.debug("Sending GET request on {}", url);
        url = "/events/" + c1.getId();
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        Event c = jacksonService.readJson(response.getContentAsString(), Event.class);

        assertEquals("spanama", c.getName());
        assertEquals("SS", c.getShortName());
        assertEquals("2017", c.getYear());
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
}
