package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.services.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import static hr.eestec_zg.frmsbackend.domain.models.CompanyType.COMPUTING;
import static org.junit.Assert.assertEquals;

public class CompanyControllerTest extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(CompanyControllerTest.class);

    @Autowired
    private CompanyService companyService;
    private Company company;

    @Before
    public void setTestData() {
        company = new Company("span", "S", COMPUTING);
        Company company2 = new Company("infobip", "IB", COMPUTING);
        companyService.createCompany(company);
        companyService.createCompany(company2);
    }

    @Test
    @WithMockUser
    public void testReadSingleCompany() throws Exception {
        final String url = "/companies/" + company.getId();
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        Company c = jacksonService.readJson(response.getContentAsString(), Company.class);

        assertEquals("span", c.getName());
        assertEquals("S", c.getShortName());
        assertEquals(COMPUTING, c.getType());
    }

}
