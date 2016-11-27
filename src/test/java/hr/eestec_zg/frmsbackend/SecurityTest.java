package hr.eestec_zg.frmsbackend;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;


public class SecurityTest extends TestBase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    public void passwordEncoderTest() {
        System.out.println(passwordEncoder.encode("password"));
    }
}
