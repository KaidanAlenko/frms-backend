package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmscore.domain.models.Company;
import hr.eestec_zg.frmscore.domain.models.CompanyType;
import hr.eestec_zg.frmscore.domain.models.Event;
import hr.eestec_zg.frmscore.domain.models.Role;
import hr.eestec_zg.frmscore.domain.models.SponsorshipType;
import hr.eestec_zg.frmscore.domain.models.Task;
import hr.eestec_zg.frmscore.domain.models.TaskStatus;
import hr.eestec_zg.frmscore.domain.models.User;
import hr.eestec_zg.frmscore.domain.models.dto.TaskDto;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TaskControllerTest.class);

    private Task task, task2, task3;
    private Event event, event2, event3;
    private User user;
    private Company company, company2;

    @Before
    public void setTestData() {
        event = new Event("E", "E", "2017");
        event2 = new Event("E2", "E2", "2017");
        event3 = new Event("E", "E", "2016");
        user = new User("F", "L", "email1", "pass1", "0001", Role.USER);
        company = new Company("COMPANY", "C", CompanyType.COMPUTING);
        company2 = new Company("COMPANY2", "C", CompanyType.COMPUTING_SCIENCE);
        companyRepository.createCompany(company);
        companyRepository.createCompany(company2);
        eventRepository.createEvent(event);
        eventRepository.createEvent(event2);
        eventRepository.createEvent(event3);
        userRepository.createUser(user);

        task = new Task(event, company, user, SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task);
        task2 = new Task(event, company, user, SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        taskRepository.createTask(task2);
        task3 = new Task(event, company, user, SponsorshipType.MATERIAL, null, null, null, TaskStatus.ACCEPTED, "");
        taskRepository.createTask(task3);
    }

    @Test
    @WithMockUser
    public void testGetActiveTasks() throws Exception {
        final String url = "/tasks";

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        //List<Task> tasks = taskRepository.getTasks();
        List<Task> tasks = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);
        assertEquals(2, tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(TaskStatus.IN_PROGRESS, tasks.get(i).getStatus());
        }
    }

    @Test
    @WithMockUser
    public void testGetTaskByID() throws Exception {
        final String url = "/tasks/" + task.getId();

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        //response 404??
        assertEquals(200, response.getStatus());

        Task t1 = jacksonService.readJson(response.getContentAsString(), Task.class);
        assertEquals(task.getId(), t1.getId());
    }

    @Test
    @WithMockUser
    public void testGetTaskByIDFail() throws Exception {
        final String url = "/tasks/" + 75555L;

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testCreateNewTask() throws Exception {
        TaskDto task4 = new TaskDto(event.getId(), company2.getId(), user.getId(), SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");

        String url = "/tasks";
        String c2Json = jacksonService.asJson(task4);

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = post(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(201, response.getStatus());

        Task returnedTask = jacksonService.readJson(response.getContentAsString(), Task.class);

        assertEquals((Long) returnedTask.getEvent().getId(), task4.getEventId());
        assertEquals((Long) returnedTask.getCompany().getId(), task4.getCompanyId());

    }

    @Test
    @WithMockUser
    public void testCreateNewTaskFail() throws Exception {
        TaskDto temp_t = new TaskDto(event.getId(), null, user.getId(), SponsorshipType.MATERIAL, null, null, null, TaskStatus.IN_PROGRESS, "");
        String url = "/tasks";
        String c2Json = jacksonService.asJson(temp_t);

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = post(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testUpdateTask() throws Exception {
        String url = "/tasks/" + task.getId();

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        //update
        Task t1 = jacksonService.readJson(response.getContentAsString(), Task.class);

        TaskDto taskDto = new TaskDto(
                t1.getEvent().getId(),
                t1.getCompany().getId(),
                t1.getAssignee().getId(),
                t1.getType(),
                t1.getCallTime(),
                t1.getMailTime(),
                t1.getFollowUpTime(),
                t1.getStatus(),
                t1.getNotes());

        taskDto.setCompanyId(company2.getId());

        //post updated task
        String c2Json = jacksonService.asJson(taskDto);
        logger.debug("Sending request on {}", url);
        response = put(url, c2Json);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        //check if task is updated
        url = "/tasks/" + task.getId();
        logger.debug("Sending request on {}", url);
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        //t1 = jacksonService.readJson(response.getContentAsString(), Task.class);
        assertEquals(company2, taskRepository.getTask(task.getId()).getCompany());


    }

    @Test
    @WithMockUser
    public void testUpdateTaskFail() throws Exception {
        String url = "/tasks/" + 777727L;

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testDeleteTask() throws Exception {
        int startingSize = taskRepository.getTasks().size();
        String url = "/tasks/" + task.getId();
        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        logger.debug("Sending request on {}", url);
        response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        //Task t=taskRepository.getTask(task.getId());
        assertEquals(startingSize - 1, taskRepository.getTasks().size());
//        logger.debug("Sending request on {}", url);
//        response = get(url);
//        logger.debug("Response: {}", response.getContentAsString());

    }

    @Test
    @WithMockUser
    public void testDeleteTaskFail() throws Exception {
        String url = "/tasks/" + 777727L;
        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());

    }


}