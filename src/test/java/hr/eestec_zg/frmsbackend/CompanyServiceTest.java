package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.exceptions.CompanyNotFoundException;
import hr.eestec_zg.frmsbackend.services.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static hr.eestec_zg.frmsbackend.domain.models.CompanyType.COMPUTING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CompanyServiceTest extends TestBase {

    @Autowired
    private CompanyService companyService;

    private Company company;
    private Company company3;

    @Before
    public void setTestData() {
        company = new Company("span", "S", COMPUTING);
        Company company2 = new Company("infobip", "IB", COMPUTING);
        companyRepository.createCompany(company);
        companyRepository.createCompany(company2);
    }

    @Test
    public void testGetCompanyByName() {
        Company company3 = companyService.getCompanyByName("span");
        assertNotNull(company3);
    }

    @Test(expected = CompanyNotFoundException.class)
    public void testGetCompanyByNameFail() {
        companyService.getCompanyByName("pasn");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        companyService.deleteCompany(null);
    }

    @Test(expected = CompanyNotFoundException.class)
    public void testCompanyNotFoundExceptionDelete() {
        Company company3 = companyService.getCompanyByName("pasn");
        companyService.deleteCompany(company3.getId());
    }

    @Test(expected = CompanyNotFoundException.class)
    public void testCompanyNotFoundExceptionPut() {
        Company company3 = new Company("spaasdasdadn", "S", COMPUTING);

        companyService.updateCompany(company3);
    }

    @Test
    public void testUpdateCompany() {
        Company company3 = companyService.getCompanyByName("span");
        company3.setAddress("asfadsfas");
        companyService.updateCompany(company3);

        Company company4 = companyService.getCompanyByName("span");
        String add = company4.getAddress();
        String shortn = company4.getShortName();
        assertEquals(add, "asfadsfas");
        assertEquals(shortn, "S");
    }

    @Test
    public void testGetCompaniesByType() {
        company3 = new Company("globallogic", "GL", "gll.com", "vukovarska 2", "poruke", CompanyType.AUTOMATIZATION);
        companyService.createCompany(company3);
        List<Company> companies = companyService.getCompaniesByType(CompanyType.AUTOMATIZATION);
        company = companyService.getCompanyById(company3.getId());
        assertEquals(1, companies.size());
        assertEquals(company,company3);
        assertTrue("There is no company with name " + company3.getName() + " stored", companies.contains(company3));
    }

    @Test
    public void testGetCompaniesByTypeFail() {
        List<Company> companies = companyService.getCompaniesByType(CompanyType.AUTOMATIZATION);
        assertEquals(0, companies.size());
    }

    @Test
    public void testGetCompanies() {
        List<Company> companies = companyService.getCompanies();
        assertEquals(2, companies.size());
    }

    @Test
    public void testCreateCompanyAutomatization() {
        company3 = new Company("globallogic", "GL", "gll.com", "vukovarska 2", "poruke", CompanyType.AUTOMATIZATION);
        companyService.createCompany(company3);
        List<Company> companies = companyService.getCompaniesByType(CompanyType.AUTOMATIZATION);

        assertTrue(companies.size() == 1 && companies.contains(company3));
    }

    @Test
    public void testCreateDeleteCompany() {
        Long a = company.getId();
        companyService.deleteCompany(a);
        a++;
        companyService.deleteCompany(a);
        List<Company> companies = companyService.getCompaniesByType(CompanyType.COMPUTING);
        assertEquals(0, companies.size());
        company3 = new Company("globallogic", "GL", "gll.com", "vukovarska 2", "poruke", CompanyType.COMPUTING);
        companyService.createCompany(company3);
        companies = companyService.getCompaniesByType(CompanyType.COMPUTING);

        assertTrue(companies.size() == 1 && companies.contains(company3));
    }

}
