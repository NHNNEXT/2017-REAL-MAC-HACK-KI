package com.amigotrip.domain;

/**
 * Created by Woohyeon on 2017. 12. 4..
 */

import org.hibernate.boot.jaxb.SourceType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

public class UserTest {

    User user = new User("email@email.com", "password", "name");

    @Before
    public void init() {
        user.encryptionPassword(new BCryptPasswordEncoder());
    }

    @Test
    public void updateUser() {
        User updateUser = new User();
        updateUser.setCity("Seoul");
        updateUser.setContents("TTTTTTTEEEEEEEESSSSSSSSSSTTTTTTTTT");

        user.updateUser(updateUser);
        System.out.println(user.getNationality());
    }
}
