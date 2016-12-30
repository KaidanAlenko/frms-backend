package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Company;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface CompanyRepository {
    void createCompany(Company company);

    void updateCompany(Company company);

    void deleteCompany(Company company);

    Company getCompany(CriteriaQuery<Company> criteria);

    List<Company> getCompanies(CriteriaQuery<Company> criteria);
}
