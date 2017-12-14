package com.amigotrip.web;

import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Woohyeon on 2017. 12. 14..
 */
@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/{userId}/emailConfirm/{key}")
    public String mail(@PathVariable long userId, @PathVariable String key) {
        return userService.confirmUserByUserId(userId, key);
    }
}
