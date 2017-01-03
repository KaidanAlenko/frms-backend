package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Role;
import hr.eestec_zg.frmsbackend.domain.models.User;

import java.util.List;
import java.util.function.Predicate;

public interface UserRepository {
    void createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUser(Long id);

    User getUserByEmail(String email);

    User getUserByPhoneNumber(String phoneNumber);

    default List<User> getUsersByFirstName(String firstName) {
        return getUsersByName(firstName, null);
    }

    default List<User> getUsersByLastName(String lastName) {
        return getUsersByName(null, lastName);
    }

    List<User> getUsersByName(String firstName, String lastName);

    List<User> getUsersByRole(Role role);

    List<User> getUsers(Predicate<User> condition);

    default List<User> getUsers() {
        return getUsers(u -> true);
    }
}
