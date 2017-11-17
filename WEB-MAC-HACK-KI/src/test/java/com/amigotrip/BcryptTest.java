package amigo.com;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

/**
 * Created by Naver on 2017. 11. 15..
 */
public class BcryptTest {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void encode() {
        String password = encoder.encode("aaaaa");
        System.out.println(password);
        assertTrue(encoder.matches("aaaaa", password));
    }
}
