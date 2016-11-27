package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.models.User;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface UserRepository {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUser(CriteriaQuery<User> criteria);
    List<User> getUsers(CriteriaQuery<User> criteria);
    User findByEmail(String email);
}
