package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.User;
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
public class DatabaseBackedUserRepository extends AbstractRepository<Long, User> implements UserRepository {

    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String ROLE = "role";

    @Override
    public void createUser(User user) {
        persist(user);
    }

    @Override
    public void updateUser(User user) {
        update(user);
    }

    @Override
    public void deleteUser(User user) {
        delete(user);
    }

    @Override
    public User getUser(Long id) {
        return getByKey(id);
    }

    @Override
    public List<User> getUsers(Predicate<User> condition) {
        CriteriaQuery<User> query = criteriaBuilder().createQuery(User.class);
        Root<User> root = query.from(User.class);
        return getUsers(query.select(root))
                .stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByEmail(String email) {

        return findByParameter(EMAIL, email);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {

        return findByParameter(PHONE_NUMBER, phoneNumber);
    }

    private User findByParameter(String name, String value) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.equal(root.get(name), value));
        return getUser(query.select(root));
    }

    @Override
    public List<User> getUsersByName(String firstName, String lastName) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(
                cb.and(
                        cb.like(cb.lower(root.get(FIRST_NAME)), RepositoryUtil.likeTerm(firstName)),
                        cb.like(cb.lower(root.get(LAST_NAME)), RepositoryUtil.likeTerm(lastName))
                ));
        return getUsers(query.select(root));
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.equal(root.get(ROLE).as(Role.class), role));
        return getUsers(query.select(root));
    }

    private User getUser(CriteriaQuery<User> query) {
        try {
            return getSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<User> getUsers(CriteriaQuery<User> query) {
        return getSession().createQuery(query).getResultList();
    }

}
