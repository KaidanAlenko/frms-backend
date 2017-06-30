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

    private static final String TEST_NAME_1 = "TestName1";
    private static final String TEST_SHORT_NAME_1 = "TN1";

    private static final String TEST_NAME_2 = "TestName2";
    private static final String TEST_SHORT_NAME_2 = "TN2";

    private static final String TEST_NAME_3 = "TestName3";
    private static final String TEST_SHORT_NAME_3 = "TN3";

    @Autowired
    private CompanyService companyService;
    private Company testCompany1;

    @Before
    public void setTestData() {
        testCompany1 = new Company(TEST_NAME_1, TEST_SHORT_NAME_1, COMPUTING);
        Company testCompany2 = new Company(TEST_NAME_2, TEST_SHORT_NAME_2, COMPUTING);

        companyService.createCompany(testCompany1);
        companyService.createCompany(testCompany2);
    }

    @Test
    @WithMockUser
    public void testReadingSingleCompany() throws Exception {
        final String url = "/companies/" + testCompany1.getId();

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());
        Company company = jacksonService.readJson(response.getContentAsString(), Company.class);

        assertEquals(TEST_NAME_1, company.getName());
        assertEquals(TEST_SHORT_NAME_1, company.getShortName());
        assertEquals(COMPUTING, company.getType());
    }

    @Test
    @WithMockUser
    public void testReadingNonExistingSingleCompany() throws Exception {
        final String url = "/companies/" + -1L;

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

    @Test
    @WithMockUser
    public void testReadingAllCompanies() throws Exception {
        final String url = "/companies";
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);

        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        List<Company> companies = jacksonService.readListOfObjects(response.getContentAsString(), Company.class);
        assertEquals(2, companies.size());

        assertEquals(testCompany1, companies.get(0));

        Company testCompany = companies.get(1);
        assertEquals(TEST_NAME_2, testCompany.getName());
        assertEquals(COMPUTING, testCompany.getType());
    }

    @Test
    @WithMockUser
    public void testDeletingCompanies() throws Exception {
        long companyId = companyService.getCompanyByName(TEST_NAME_1).getId();
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
    public void testFilteringCompaniesByType() throws Exception {
        String url = "/companies/search?type=COMPUTING";

        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        List<Company> companies = jacksonService.readListOfObjects(response.getContentAsString(), Company.class);
        assertEquals(2, companies.size());
    }

    @Test
    @WithMockUser
    public void testCreationOfCompanies() throws Exception {
        Company testCompany1 = new Company(TEST_NAME_3, TEST_SHORT_NAME_3, COMPUTING);
        String url = "/companies";

        String testCompanyJson = jacksonService.asJson(testCompany1);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = post(url, testCompanyJson);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(201, response.getStatus());

        url = "/companies";

        response = get(url);
        List<Company> companies = jacksonService.readListOfObjects(response.getContentAsString(), Company.class);
        assertEquals(3, companies.size());

        Company testCompany2 = companies.get(2);
        assertEquals(TEST_NAME_3, testCompany2.getName());
        assertEquals(TEST_SHORT_NAME_3, testCompany2.getShortName());
        assertEquals(COMPUTING, testCompany2.getType());
    }

    @Test
    @WithMockUser
    public void testUpdatingCompany() throws Exception {
        Company testCompany = companyService.getCompanyByName("TestName1");
        String url = "/companies/" + testCompany.getId();

        logger.debug("Sending GET request on {}", url);

        MockHttpServletResponse response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        testCompany.setName(TEST_NAME_3);
        testCompany.setShortName(TEST_SHORT_NAME_3);

        String testCompanyJson = jacksonService.asJson(testCompany);

        logger.debug("Sending PUT request on {}", url);

        response = put(url, testCompanyJson);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(200, response.getStatus());

        testCompany = companyService.getCompanyByName(TEST_NAME_3);

        logger.debug("Sending GET request on {}", url);

        url = "/companies/" + testCompany.getId();
        response = get(url);
        logger.debug("Response: {}", response.getContentAsString());

        Company company = jacksonService.readJson(response.getContentAsString(), Company.class);
        assertEquals(TEST_NAME_3, company.getName());
        assertEquals(TEST_SHORT_NAME_3, company.getShortName());
        assertEquals(COMPUTING, company.getType());
    }

    @Test
    @WithMockUser
    public void testUpdatingNonExistingCompany() throws Exception {
        Company testCompany = new Company(TEST_NAME_3, TEST_SHORT_NAME_3, COMPUTING);
        String url = "/companies/" + -1L;

        String testCompanyJson = jacksonService.asJson(testCompany);
        logger.debug("Sending request on {}", url);

        MockHttpServletResponse response = put(url, testCompanyJson);
        logger.debug("Response: {}", response.getContentAsString());

        assertEquals(404, response.getStatus());
    }

}
