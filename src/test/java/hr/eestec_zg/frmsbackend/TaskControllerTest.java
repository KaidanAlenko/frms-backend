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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TaskControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TaskControllerTest.class);

    private static final String TEST_EVENT_NAME_1 = "TestName1";
    private static final String TEST_EVENT_SHORT_NAME_1 = "TN1";
    private static final String TEST_EVENT_YEAR_1 = "2017";

    private static final String TEST_EVENT_NAME_2 = "TestName2";
    private static final String TEST_EVENT_SHORT_NAME_2 = "TN2";
    private static final String TEST_EVENT_YEAR_2 = "2017";

    private static final String TEST_EVENT_NAME_3 = "TestName3";
    private static final String TEST_EVENT_SHORT_NAME_3 = "TN3";
    private static final String TEST_EVENT_YEAR_3 = "2017";

    private static final String TEST_COMPANY_NAME_1 = "TestCompanyName1";
    private static final String TEST_COMPANY_SHORT_NAME_1 = "TC1";

    private static final String TEST_COMPANY_NAME_2 = "TestCompanyName2";
    private static final String TEST_COMPANY_SHORT_NAME_2 = "TC2";

    private static final String TEST_USER_FIRST_NAME = "TestFirstName";
    private static final String TEST_USER_LAST_NAME = "TestLastName";
    private static final String TEST_USER_MAIL = "Test@Mail.MM";

    private static final String DUMMY_VALUE = "DummyValue";

    private Task testTask1;
    private Event testEvent1;
    private User testUser;
    private Company testCompany2;

    @Before
    public void setTestData() {
        testEvent1 = new Event(TEST_EVENT_NAME_1, TEST_EVENT_SHORT_NAME_1, TEST_EVENT_YEAR_1);
        Event testEvent2 = new Event(TEST_EVENT_NAME_2, TEST_EVENT_SHORT_NAME_2, TEST_EVENT_YEAR_2);
        Event testEvent3 = new Event(TEST_EVENT_NAME_3, TEST_EVENT_SHORT_NAME_3, TEST_EVENT_YEAR_3);

        testUser = new User(
                TEST_USER_FIRST_NAME,
                TEST_USER_LAST_NAME,
                TEST_USER_MAIL,
                DUMMY_VALUE,
                DUMMY_VALUE,
                Role.USER, null
        );

        Company testCompany1 = new Company(TEST_COMPANY_NAME_1, TEST_COMPANY_SHORT_NAME_1, CompanyType.COMPUTING);
        testCompany2 = new Company(TEST_COMPANY_NAME_2, TEST_COMPANY_SHORT_NAME_2, CompanyType.COMPUTING_SCIENCE);

        companyRepository.createCompany(testCompany1);
        companyRepository.createCompany(testCompany2);

        eventRepository.createEvent(testEvent1);
        eventRepository.createEvent(testEvent2);
        eventRepository.createEvent(testEvent3);

        userRepository.createUser(testUser);

        testTask1 = new Task(
                testEvent1,
                testCompany1,
                testUser,
                SponsorshipType.MATERIAL,
                null, null, null,
                TaskStatus.IN_PROGRESS,
                ""
        );
        taskRepository.createTask(testTask1);

        Task testTask2 = new Task(
                testEvent1,
                testCompany1,
                testUser,
                SponsorshipType.MATERIAL,
                null, null, null,
                TaskStatus.IN_PROGRESS,
                ""
        );
        taskRepository.createTask(testTask2);

        Task testTask3 = new Task(
                testEvent1,
                testCompany1,
                testUser,
                SponsorshipType.MATERIAL,
                null, null, null,
                TaskStatus.ACCEPTED,
                ""
        );
        taskRepository.createTask(testTask3);
    }

    @Test
    @WithMockUser
    public void testGetActiveTasks() throws Exception {
        final String url = "/tasks";

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        List<Task> tasks = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);
        assertEquals(2, tasks.size());

        Optional<Task> anyNotInProgressTask = tasks.stream()
                .filter(t -> !t.getStatus().equals(TaskStatus.IN_PROGRESS))
                .findAny();

        assertFalse("Not all tasks are of status IN_PROGRESS", anyNotInProgressTask.isPresent());
    }

    @Test
    @WithMockUser
    public void testGetTaskByID() throws Exception {
        final String url = "/tasks/" + testTask1.getId();

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        Task t1 = jacksonService.readJson(response.getContentAsString(), Task.class);
        assertEquals(testTask1.getId(), t1.getId());
    }

    @Test
    @WithMockUser
    public void testGetTaskByIDFail() throws Exception {
        final String url = "/tasks/" + -1L;

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void filterTasks() throws Exception {
        final String url = "/tasks/search?status=IN_PROGRESS&type=MATERIAL";

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        List<Task> foundTasks = jacksonService.readListOfObjects(response.getContentAsString(), Task.class);

        assertEquals(2, foundTasks.size());
    }

    @Test
    @WithMockUser
    public void testCreateNewTask() throws Exception {
        TaskDto testTaskDto = new TaskDto(
                testEvent1.getId(),
                testCompany2.getId(),
                testUser.getId(),
                SponsorshipType.MATERIAL,
                null, null, null,
                TaskStatus.IN_PROGRESS,
                ""
        );

        String url = "/tasks";
        String taskDtoJson = jacksonService.asJson(testTaskDto);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, taskDtoJson);
        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(201, response.getStatus());

        Task returnedTask = jacksonService.readJson(response.getContentAsString(), Task.class);

        assertEquals((Long) returnedTask.getEvent().getId(), testTaskDto.getEventId());
        assertEquals((Long) returnedTask.getCompany().getId(), testTaskDto.getCompanyId());

    }

    @Test
    @WithMockUser
    public void testCreateNewTaskFail() throws Exception {
        TaskDto testTaskDto = new TaskDto(
                testEvent1.getId(),
                null,
                testUser.getId(),
                SponsorshipType.MATERIAL,
                null, null, null,
                TaskStatus.IN_PROGRESS,
                ""
        );

        String url = "/tasks";
        String taskDtoJson = jacksonService.asJson(testTaskDto);

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, taskDtoJson);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(400, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testUpdateTask() throws Exception {
        String url = "/tasks/" + testTask1.getId();

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        // update
        Task testTask = jacksonService.readJson(response.getContentAsString(), Task.class);

        TaskDto taskDto = new TaskDto(
                testTask.getEvent().getId(),
                testTask.getCompany().getId(),
                testTask.getAssignee().getId(),
                testTask.getType(),
                testTask.getCallTime(),
                testTask.getMailTime(),
                testTask.getFollowUpTime(),
                testTask.getStatus(),
                testTask.getNotes());

        taskDto.setCompanyId(testCompany2.getId());

        // POST updated testTask1
        String testTaskDto = jacksonService.asJson(taskDto);

        logger.debug("Sending request on {}", url);

        response = put(url, testTaskDto);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        // check if testTask1 is updated
        url = "/tasks/" + testTask1.getId();

        logger.debug("Sending request on {}", url);

        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        assertEquals(testCompany2, taskRepository.getTask(testTask1.getId()).getCompany());
    }

    @Test
    @WithMockUser
    public void testUpdateTaskFail() throws Exception {
        String url = "/tasks/" + -10L;

        logger.debug("Sending request on {}", url);
        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testDeleteTask() throws Exception {
        int startingSize = taskRepository.getTasks().size();

        String url = "/tasks/" + testTask1.getId();

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        logger.debug("Sending request on {}", url);
        response = delete(url);

        logger.debug("Response: {}", response.getContentAsString());
        assertEquals(200, response.getStatus());

        assertEquals(startingSize - 1, taskRepository.getTasks().size());
    }

    @Test
    @WithMockUser
    public void testDeleteTaskFail() throws Exception {
        String url = "/tasks/" + -10L;

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = delete(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }


}