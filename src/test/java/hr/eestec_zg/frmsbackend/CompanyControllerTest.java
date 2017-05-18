package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmscore.domain.models.Company;
import hr.eestec_zg.frmscore.services.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static hr.eestec_zg.frmscore.domain.models.CompanyType.COMPUTING;
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

    @Test
    @WithMockUser
    public void testReadSingleCompanyFail() throws Exception {
        final String url = "/companies/" + 77777L;
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testReadCompanies() throws Exception {
        final String url = "/companies";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        List<Company> c = jacksonService.readListOfObjects(response.getContentAsString(), Company.class);
        assertEquals(2, c.size());
        Company c2 = c.get(1);
        assertEquals(company, c.get(0));
        assertEquals("infobip", c2.getName());
        assertEquals(COMPUTING, c2.getType());
    }

    @Test
    @WithMockUser
    public void testDeleteReadCompanies() throws Exception {
        long companyId = companyService.getCompanyByName("span").getId();
        String url = "/companies/" + companyId;
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
    public void testCreateReadCompany() throws Exception {
        Company c2 = new Company("spanama", "SS", COMPUTING);
        String url = "/companies";
        String c2Json = jacksonService.asJson(c2);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(201, response.getStatus());

        url = "/companies";
        response = get(url);
        List<Company> c = jacksonService.readListOfObjects(response.getContentAsString(), Company.class);
        assertEquals(3, c.size());
        Company c1 = c.get(2);

        assertEquals("spanama", c1.getName());
        assertEquals("SS", c1.getShortName());
        assertEquals(COMPUTING, c1.getType());
    }

    @Test
    @WithMockUser
    public void testPutReadCompany() throws Exception {
        Company c1 = companyService.getCompanyByName("span");

        String url = "/companies/" + c1.getId();
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

        c1 = companyService.getCompanyByName("spanama");
        logger.debug("Sending GET request on {}", url);
        url = "/companies/" + c1.getId();
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());
        Company c = jacksonService.readJson(response.getContentAsString(), Company.class);

        assertEquals("spanama", c.getName());
        assertEquals("SS", c.getShortName());
        assertEquals(COMPUTING, c.getType());
    }

    @Test
    @WithMockUser
    public void testPutFail() throws Exception {
        Company c2 = new Company("spanama", "SS", COMPUTING);
        String url = "/companies/" + 777727L;
        String c2Json = jacksonService.asJson(c2);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = put(url, c2Json);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

}
