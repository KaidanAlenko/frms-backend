package hr.eestec_zg.frmsbackend.controllers;

import hr.eestec_zg.frmsbackend.domain.CompanyRepository;
import hr.eestec_zg.frmsbackend.domain.models.Company;
import hr.eestec_zg.frmsbackend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable("id") Long id) {
        if (id != null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        return companyService.getCompanyById(id);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public List<Company> getCompanies() {
        //
        return companyService.getCompanies();
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value	=	HttpStatus.OK)
    public void deleteCompany(@PathVariable("id") Long id) {
        if (id != null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        companyService.deleteCompany(id);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public @ResponseBody Company postCompany(@RequestBody Company company) {
        companyService.createCompany(company);
        return company;
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value	=	HttpStatus.OK)
    public void putCompany(@PathVariable("id") Long id, @RequestBody Company company) {
        if (id != null) {
            throw new IllegalArgumentException("Id must not be null value");
        }
        companyService.updateCompany(company);
    }

}
