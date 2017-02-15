package hr.eestec_zg.frmsbackend.config.security;

import hr.eestec_zg.frmsbackend.domain.models.User;

public class UserDetails {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;

    public UserDetails(long id, String firstName, String lastName, String email, String phoneNumber, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public static UserDetails createDetailsFromUser(User user) {
        return new UserDetails(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().name());
    }
}
