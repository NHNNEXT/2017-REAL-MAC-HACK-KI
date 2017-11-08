package amigo.com.web;

import amigo.com.domain.User;
import amigo.com.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;

/**
 * Created by Naver on 2017. 11. 8..
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class ApiUserController {
    @Resource
    private UserRepository userRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        log.debug("{}", user);
        return null;
    }

}
