package hr.eestec_zg.frmsbackend;

import config.TestAppConfig;
import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.EventRepository;
import hr.eestec_zg.frmsbackend.domain.TaskRepository;
import hr.eestec_zg.frmsbackend.domain.UserRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@WebAppConfiguration
@Transactional
public abstract class TestBase {
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected TaskRepository taskRepository;
    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected UserRepository emptyUserRepository;

    MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
}
