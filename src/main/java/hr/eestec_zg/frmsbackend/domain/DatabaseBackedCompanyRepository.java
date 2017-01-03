package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Company;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DatabaseBackedCompanyRepository extends AbstractRepository<Long, Company> implements CompanyRepository {

    private static final String NAME = "name";
    private static final String SHORT_NAME = "shortName";

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
    public Company getCompany(Long id) {
        return getByKey(id);
    }

    @Override
    public Company getCompanyByName(String name) {
        String searchTerm = "%" + name.toLowerCase() + "%";
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> root = query.from(Company.class);
        query.where(
                cb.or(
                        cb.like(cb.lower(root.get(NAME)), searchTerm),
                        cb.like(cb.lower(root.get(SHORT_NAME)), searchTerm)));
        return getCompany(query.select(root));
    }

    @Override
    public List<Company> getCompanies(Predicate<Company> condition) {
        CriteriaQuery<Company> query = criteriaBuilder().createQuery(Company.class);
        Root<Company> root = query.from(Company.class);
        return getCompanies(query.select(root))
                .stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    private Company getCompany(CriteriaQuery<Company> query) {
        try {
            return getSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<Company> getCompanies(CriteriaQuery<Company> query) {
        return getSession().createQuery(query).getResultList();
    }
}
