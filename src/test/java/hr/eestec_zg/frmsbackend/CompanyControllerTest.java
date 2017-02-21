package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.services.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static hr.eestec_zg.frmsbackend.domain.models.CompanyType.COMPUTING;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CompanyControllerTest {
    @Autowired
    private CompanyService companyService;
    private Company company;
    private MockMvc mockMvc;
    @Autowired
    private	WebApplicationContext wac;

    @Before
    public void setTestData() {
        company = new Company("span", "S", COMPUTING);
        Company company2 = new Company("infobip", "IB", COMPUTING);
        companyService.createCompany(company);
        companyService.createCompany(company2);
        this.mockMvc	=
                MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void testReadSingleCompany() throws Exception {
        mockMvc.perform(get("/companies/"+company.getId()
                ))
                .andExpect(status().isOk())
               // .andExpect(jsonPath("$.name", is(company.getName())))
                //.andExpect(jsonPath("$.shortname", is(company.getShortName())))
                //.andExpect(content().string("{\"name\":\"span\",\"shortName\":S,}"));
              //  .andExpect(jsonPath("$.type", is(company.getType())));
        ;
    }

}
