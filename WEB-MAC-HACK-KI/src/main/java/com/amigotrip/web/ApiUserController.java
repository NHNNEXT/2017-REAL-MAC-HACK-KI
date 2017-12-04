package com.amigotrip.web;

import com.amigotrip.domain.User;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> body) {
        if(userService.isDuplicatedEmail(body.get("email"))) {
            return new ResponseEntity<User>(
                    new User(),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<User>(
                userService.signup(body.get("email"), body.get("password"), body.get("name")),
                HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable long userId) {
        User user = userService.findUserById(userId);
        if(!userService.isConfirmedUserId(userId)) {
            return new ResponseEntity<User>(user,
                    HttpStatus.OK);
        }
        return new ResponseEntity<User>(user,
                HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User user) {
        return new ResponseEntity<User>(userService.updateUser(userId, user),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> body, HttpSession httpSession) {
        return new ResponseEntity<User>(userService.login(body.get("email"), body.get("password"), httpSession),
                HttpStatus.OK);
    }
}
