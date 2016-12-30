package hr.eestec_zg.frmsbackend.domain.models;

import java.io.Serializable;

public enum Role implements Serializable {
    USER        ("USER"),
    COORDINATOR ("COORDINATOR"),
    ADMIN       ("ADMIN");

    String role;

    private Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
