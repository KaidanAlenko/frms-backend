package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;


import java.util.List;

public interface CompanyService {
    void createCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Long companyId);
    Company getCompanyByName(String name);
    Company getCompanyById(Long id);
    List<Company> getCompanies();
    List<Company> getCompaniesByType(CompanyType companyType);
}
