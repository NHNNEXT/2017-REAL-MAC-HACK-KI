package amigo.com.web;

import amigo.com.domain.User;
import amigo.com.domain.UserRepository;
import amigo.com.mail.AmigoMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Naver on 2017. 11. 8..
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class ApiUserController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    AmigoMailSender amigoMailSender;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Result> createUser(@RequestBody User user, HttpServletResponse response) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<Result>(
                    new Result(),
                    HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        }

        user.encryptionPassword(bCryptPasswordEncoder);
        User savedUser = userRepository.save(user);
        amigoMailSender.sendEmailConfirmMail(savedUser);
        return new ResponseEntity<Result>(
                new Result("/user/" + savedUser.getId()),
                HttpStatus.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable long userId) {
        if(userRepository.findOne(userId).isConfirmdUser()) {
            return new ResponseEntity<User>(userRepository.findOne(userId),
                    HttpStatus.valueOf(HttpStatus.OK.value()));
        }
        return new ResponseEntity<User>(userRepository.findOne(userId),
                HttpStatus.valueOf(HttpStatus.ACCEPTED.value()));
    }
}
