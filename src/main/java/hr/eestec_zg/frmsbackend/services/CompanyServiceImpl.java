package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.domain.models.CompanyType;
import hr.eestec_zg.frmsbackend.exceptions.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void createCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company not defined");
        }

        companyRepository.createCompany(company);
    }

    @Override
    public void updateCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company not defined");
        }

        companyRepository.updateCompany(company);
    }

    @Override
    public void deleteCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Id not defined");
        }

        Company company = companyRepository.getCompany(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }

        companyRepository.deleteCompany(company);
    }

    @Override
    public Company getCompanyByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name not defined");
        }

        Company companyByName = companyRepository.getCompanyByName(name);

        if (companyByName == null) {
            throw new CompanyNotFoundException();
        }
        return companyByName;
    }

    @Override
    public List<Company> getCompaniesByType(CompanyType companyType) {
        if (companyType == null) {
            throw new IllegalArgumentException("Company type not defined");
        }

        List<Company> allCompaniesByType = companyRepository.getCompanies((e) -> e.getType() == companyType);

        if (allCompaniesByType == null) {
            throw new CompanyNotFoundException();
        }
        return allCompaniesByType;
    }
}
