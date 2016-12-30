package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class CompanyRepositoryImpl extends AbstractRepository<Long, Company> implements CompanyRepository {

    @Override
    public void createCompany(Company company) {
        persist(company);
    }

    @Override
    public void updateCompany(Company company) {
        update(company);
    }

    @Override
    public void deleteCompany(Company company) {
        delete(company);
    }

    @Override
    public Company getCompany(CriteriaQuery<Company> criteria) {
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Company> getCompanies(CriteriaQuery<Company> criteria) {
        return getSession().createQuery(criteria).getResultList();
    }
}
