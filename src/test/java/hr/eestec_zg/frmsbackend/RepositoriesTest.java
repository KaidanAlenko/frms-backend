package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.EventRepository;
import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.domain.models.SponsorshipType;
import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RepositoriesTest extends TestBase {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createAndGetTask() {
        Event event = new Event("Radionica o informacijskoj sigurnosti", "FERSEC", "2016");
        eventRepository.createEvent(event);
        Company company = new Company("Privredna banka Osijek", "PBO", "www.pbo.com", "Maslenička 5, 22300 Knin", null, CompanyType.COMPUTING);
        companyRepository.createCompany(company);
        User user = userRepository.findByEmail("john.doe@gmail.com");
        Task task = new Task();
        task.setAssignee(user);
        task.setCompany(company);
        task.setEvent(event);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setType(SponsorshipType.FINANCIAL);
        taskRepository.createTask(task);
    }

}
