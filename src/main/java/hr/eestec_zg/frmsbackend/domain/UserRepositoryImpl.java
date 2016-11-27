package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractRepository<Long, User> implements UserRepository {

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
    public User getUser(CriteriaQuery<User> criteria) {
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<User> getUsers(CriteriaQuery<User> criteria) {
        return getSession().createQuery(criteria).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.where(cb.equal(root.get("email"), email));
        return getUser(criteria);
    }

}
