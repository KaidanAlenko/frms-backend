package hr.eestec_zg.frmsbackend;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;


public class SecurityTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(SecurityTest.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser
    public void passwordEncoderTest() {
        logger.info("Password hash: {}", passwordEncoder.encode("password"));
    }
}
