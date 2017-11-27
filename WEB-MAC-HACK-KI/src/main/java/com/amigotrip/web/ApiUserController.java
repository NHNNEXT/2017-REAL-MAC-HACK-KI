package com.amigotrip.web;

import com.amigotrip.domain.User;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Naver on 2017. 11. 8..
 */
@RestController
@Slf4j
@RequestMapping("/users")
public class ApiUserController {
    @Resource
    private UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.isDuplicatedEmail(user.getEmail())) {
            return new ResponseEntity<User>(
                    user,
                    HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        }

        return new ResponseEntity<User>(
                userService.signup(user),
                HttpStatus.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable long userId) {
        User user = userService.findUserById(userId);
        if(!userService.isConfirmedUserId(userId)) {
            return new ResponseEntity<User>(user,
                    HttpStatus.valueOf(HttpStatus.OK.value()));
        }
        return new ResponseEntity<User>(user,
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpSession httpSession) {
        return new ResponseEntity<User>(userService.login(user, httpSession),
                HttpStatus.OK);
    }
}
