package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.EventRepository;
import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.SponsorshipType;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static hr.eestec_zg.frmsbackend.utilities.Util.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RepositoriesTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(RepositoriesTest.class);

    private static final String TEST_EVENT_NAME = "Radionica o informacijskoj sigurnosti";
    private static final String TEST_EVENT_SHORT_NAME = "FERSEC";
    private static final String TEST_EVENT_YEAR = "2016";
    private static final String TEST_SEARCH_EVENT_NO_1 = "informacij";
    private static final String TEST_SEARCH_EVENT_NO_2 = "fersec";
    private static final String TEST_UPDATE_EVENT_NAME = "INFOSEC";

    private static final String TEST_COMPANY_NAME = "Privredna banka Zagreb";
    private static final String TEST_COMPANY_SHORT_NAME = "PBZ";
    private static final CompanyType TEST_COMPANY_TYPE = CompanyType.COMPUTING;
    private static final String TEST_SEARCH_COMPANY_NO_1 = "banka";
    private static final String TEST_SEARCH_COMPANY_NO_2 = "pbz";
    private static final String TEST_UPDATE_COMPANY_NAME = "PeBeZe";

    private static final String TEST_USER_FIRST_NAME = "John";
    private static final String TEST_USER_LAST_NAME = "Doe";
    private static final String TEST_USER_EMAIL = "john.doe" + randomUUID() + "@gmail.com";
    private static final String TEST_USER_PHONE_NUMBER = "+385911111111";
    private static final Role TEST_USER_ROLE = Role.COORDINATOR;
    private static final String TEST_USER_PASSWORD = "password";
    private static final String TEST_SEARCH_USER_NO_1 = "doe";
    private static final String TEST_SEARCH_USER_NO_2 = "ohn";
    private static final String TEST_UPDATE_USER_NAME = "Jane";

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void eventRepositoryTest() {

        /* creating event */
        Event event = new Event(TEST_EVENT_NAME, TEST_EVENT_SHORT_NAME, TEST_EVENT_YEAR);
        eventRepository.createEvent(event);
        assertFalse("There should be an event in repository", eventRepository.getEvents().isEmpty());

        /* searching for events */
        Event foundEvent = eventRepository.getEventByName(TEST_EVENT_SHORT_NAME);
        assertNotNull("There should be one event in repository by name: " + TEST_EVENT_SHORT_NAME, foundEvent);

        logger.debug("Created and found event by short name: {}", foundEvent);

        foundEvent = eventRepository.getEventByName(TEST_SEARCH_EVENT_NO_1);
        assertNotNull("There should be one event in repository by name: " + TEST_EVENT_NAME, foundEvent);
        logger.debug("Found event by '{}' part of name: {}", TEST_SEARCH_EVENT_NO_1, foundEvent);

        foundEvent = eventRepository.getEventByName(TEST_SEARCH_EVENT_NO_2);
        assertNotNull("There should be one event in repository by name: " + TEST_EVENT_NAME, foundEvent);
        logger.debug("Found event by '{}' part of name: {}", TEST_SEARCH_EVENT_NO_2, foundEvent);

        assertNotNull("There should be one event in repository by id: " + foundEvent.getId(),
                eventRepository.getEvent(foundEvent.getId()));

        List<Event> eventsByYear = eventRepository.getEventsByYear(TEST_EVENT_YEAR);
        assertFalse("There should be event with year" + TEST_EVENT_YEAR, eventsByYear.isEmpty());

        eventsByYear.forEach(e -> assertEquals(TEST_EVENT_YEAR, e.getYear()));
        logger.debug("Found events by year '{}': {}", TEST_EVENT_YEAR, eventsByYear);

        /* updating event */
        foundEvent.setShortName(TEST_UPDATE_EVENT_NAME);
        eventRepository.updateEvent(foundEvent);
        foundEvent = eventRepository.getEventByName(TEST_UPDATE_EVENT_NAME);
        assertNotNull("There should be one event in repository by short name: " + TEST_UPDATE_EVENT_NAME, foundEvent);
        assertNull("There shouldn't be event in repository with name " + TEST_EVENT_SHORT_NAME,
                eventRepository.getEventByName(TEST_EVENT_SHORT_NAME));
        logger.debug("Updated event: {}", foundEvent);

        /* deleting event */
        eventRepository.deleteEvent(foundEvent);
        assertTrue("There shouldn't be an event in repository", eventRepository.getEvents().isEmpty());
        logger.debug("Deleted event: {}", foundEvent);
    }

    @Test
    public void companyRepositoryTest() {

        /* creating company */
        Company company = new Company(TEST_COMPANY_NAME, TEST_COMPANY_SHORT_NAME, TEST_COMPANY_TYPE);
        companyRepository.createCompany(company);
        assertFalse("There should be an company in repository", companyRepository.getCompanies().isEmpty());

        /* searching for companies */
        Company foundCompany = companyRepository.getCompanyByName(TEST_COMPANY_SHORT_NAME);
        assertNotNull("There should be one company in repository by name: " + TEST_COMPANY_SHORT_NAME, foundCompany);

        logger.debug("Created and found company by short name: {}", TEST_COMPANY_SHORT_NAME);

        foundCompany = companyRepository.getCompanyByName(TEST_SEARCH_COMPANY_NO_1);
        assertNotNull("There should be one company in repository by name: " + TEST_COMPANY_NAME, foundCompany);
        logger.debug("Found company by '{}' part of name: {}", TEST_SEARCH_COMPANY_NO_1, foundCompany);

        foundCompany = companyRepository.getCompanyByName(TEST_SEARCH_COMPANY_NO_2);
        assertNotNull("There should be one company in repository by name: " + TEST_COMPANY_NAME, foundCompany);
        logger.debug("Found company by '{}' part of name: {}", TEST_SEARCH_COMPANY_NO_2, foundCompany);

        assertNotNull("There should be one company in repository by id: " + foundCompany.getId(),
                companyRepository.getCompany(foundCompany.getId()));

        /* updating company */
        foundCompany.setShortName(TEST_UPDATE_COMPANY_NAME);
        companyRepository.updateCompany(foundCompany);
        foundCompany = companyRepository.getCompanyByName(TEST_UPDATE_COMPANY_NAME);
        assertNotNull("There should be one company in repository by short name: " + TEST_UPDATE_COMPANY_NAME,
                foundCompany);
        assertNull("There shouldn't be company in repository with name " + TEST_COMPANY_SHORT_NAME,
                companyRepository.getCompanyByName(TEST_COMPANY_SHORT_NAME));
        logger.debug("Updated company: {}", foundCompany);

        /* deleting company */
        companyRepository.deleteCompany(foundCompany);
        assertTrue("There shouldn't be an company in repository", companyRepository.getCompanies().isEmpty());
        logger.debug("Deleted company: {}", foundCompany);
    }

    @Test
    public void userRepositoryTest() {

        /* creating user */
        User user = new User();
        user.setFirstName(TEST_USER_FIRST_NAME);
        user.setLastName(TEST_USER_LAST_NAME);
        user.setEmail(TEST_USER_EMAIL);
        user.setPhoneNumber(TEST_USER_PHONE_NUMBER);
        user.setRole(TEST_USER_ROLE);
        user.setPassword(passwordEncoder.encode(TEST_USER_PASSWORD));

        userRepository.createUser(user);
        assertFalse("There should be an user in repository", userRepository.getUsers().isEmpty());

        /* searching for users */
        User foundUser = userRepository.getUserByEmail(TEST_USER_EMAIL);
        assertNotNull("There should be one user in repository with email: " + TEST_USER_EMAIL, foundUser);
        logger.debug("Created and found user by email: {}", TEST_USER_EMAIL);

        foundUser = userRepository.getUserByPhoneNumber(TEST_USER_PHONE_NUMBER);
        assertNotNull("There should be one user in repository with phone number: " + TEST_USER_PHONE_NUMBER, foundUser);
        logger.debug("Found user by '{}' phone number: {}", TEST_USER_PHONE_NUMBER, foundUser);

        List<User> foundUsers = userRepository.getUsersByRole(Role.COORDINATOR);
        assertFalse("There should be user in repository with role " + TEST_USER_ROLE, foundUsers.isEmpty());

        foundUsers = userRepository.getUsersByRole(Role.USER);
        assertTrue("There shouldn't be user in repository with role " + Role.USER, foundUsers.isEmpty());

        foundUsers = userRepository.getUsersByLastName(TEST_SEARCH_USER_NO_1);
        assertFalse("There should be user in repository", foundUsers.isEmpty());

        foundUsers = userRepository.getUsersByFirstName(TEST_SEARCH_USER_NO_2);
        assertFalse("There should be user in repository", foundUsers.isEmpty());

        foundUsers = userRepository.getUsersByName(TEST_SEARCH_USER_NO_2, TEST_SEARCH_USER_NO_1);
        assertFalse("There should be user in repository", foundUsers.isEmpty());

        foundUser = foundUsers.get(0);
        assertNotNull("There should be one user in repository by name: " + TEST_SEARCH_USER_NO_1, foundUser);
        logger.debug("Found user by name '{}, {}': {}", TEST_SEARCH_USER_NO_1, TEST_SEARCH_USER_NO_2, foundUser);

        assertNotNull("There should be one user in repository by id: " + foundUser.getId(),
                userRepository.getUser(foundUser.getId()));

        /* updating user */
        foundUser.setFirstName(TEST_UPDATE_USER_NAME);
        userRepository.updateUser(foundUser);
        foundUser = userRepository.getUsersByFirstName(TEST_UPDATE_USER_NAME).get(0);
        assertNotNull("There should be one user in repository by first name: " + TEST_UPDATE_USER_NAME,
                foundUser);
        assertEquals("There shouldn't be user in repository with name " + TEST_USER_FIRST_NAME,
                0, userRepository.getUsersByFirstName(TEST_USER_FIRST_NAME).size());
        logger.debug("Updated user: {}", foundUser);

        /* deleting user */
        userRepository.deleteUser(foundUser);
        assertTrue("There shouldn't be an user in repository", userRepository.getUsers().isEmpty());
        logger.debug("Deleted user: {}", foundUser);
    }

    @Test
    public void taskRepositoryTest() {

        /* Prerequisites */
        final Event event = sampleEvent();
        final Company company = sampleCompany();
        final User assignee = sampleUser();
        eventRepository.createEvent(event);
        companyRepository.createCompany(company);
        userRepository.createUser(assignee);

        /* creating task */
        Task task = new Task();
        task.setEvent(event);
        task.setCompany(company);
        task.setAssignee(assignee);
        task.setType(SponsorshipType.FINANCIAL);
        task.setStatus(TaskStatus.IN_PROGRESS);

        taskRepository.createTask(task);
        assertFalse("There should be an task in repository", taskRepository.getTasks().isEmpty());

        /* searching for tasks */
        List<Task> foundTasks = taskRepository.getTasksByEvent(event);
        assertEquals(1, foundTasks.size());
        assertNotNull("There should be task in repository with event: " + event, foundTasks.get(0));
        logger.debug("Created and found task by event: {}", event);

        foundTasks = taskRepository.getTasksByCompany(company);
        assertEquals(1, foundTasks.size());
        assertNotNull("There should be one task in repository with company: " + company, foundTasks.get(0));
        logger.debug("Found task by '{}' company: {}", company, task);

        foundTasks = taskRepository.getTasksByAssignee(assignee);
        assertFalse("There should be task in repository with assignee " + assignee, foundTasks.isEmpty());

        foundTasks = taskRepository.getTaskByStatus(TaskStatus.DECLINED);
        assertTrue("There shouldn't be task in repository with status " + TaskStatus.DECLINED, foundTasks.isEmpty());

        foundTasks = taskRepository.getTaskByStatus(TaskStatus.IN_PROGRESS);
        assertFalse("There should be task in repository with status " + TaskStatus.IN_PROGRESS, foundTasks.isEmpty());

        Long foundTaskId = foundTasks.get(0).getId();
        Task foundTask = taskRepository.getTask(foundTaskId);
        assertNotNull("There should be task in repository with id " + foundTaskId, foundTask);

        /* updating task */
        foundTask.setStatus(TaskStatus.ACCEPTED);
        taskRepository.updateTask(foundTask);
        foundTasks = taskRepository.getTaskByStatus(TaskStatus.ACCEPTED);
        assertFalse("There should be one task in repository with status " + TaskStatus.ACCEPTED, foundTasks.isEmpty());
        foundTasks = taskRepository.getTaskByStatus(TaskStatus.IN_PROGRESS);
        assertTrue("There shouldn't be task in repository with status " + TaskStatus.IN_PROGRESS, foundTasks.isEmpty());
        logger.debug("Updated user: {}", foundTask);

        /* deleting task */
        taskRepository.deleteTask(foundTask);
        assertTrue("There shouldn't be an task in repository", taskRepository.getTasks().isEmpty());
        logger.debug("Deleted user: {}", foundTask);
    }

    private Event sampleEvent() {
        return new Event(TEST_EVENT_NAME, TEST_EVENT_SHORT_NAME, TEST_EVENT_YEAR);
    }

    private Company sampleCompany() {
        return new Company(TEST_COMPANY_NAME, TEST_COMPANY_SHORT_NAME, TEST_COMPANY_TYPE);
    }

    private User sampleUser() {
        User user = new User();
        user.setFirstName(TEST_USER_FIRST_NAME);
        user.setLastName(TEST_USER_LAST_NAME);
        user.setEmail(TEST_USER_EMAIL);
        user.setPhoneNumber(TEST_USER_PHONE_NUMBER);
        user.setRole(TEST_USER_ROLE);
        user.setPassword(passwordEncoder.encode(TEST_USER_PASSWORD));
        return user;
    }
}
