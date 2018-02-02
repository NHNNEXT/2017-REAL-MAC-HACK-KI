package com.amigotrip.web;

import com.amigotrip.domain.User;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

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

    @GetMapping("/profile/{userId}")
    public String profile(Principal principal, @PathVariable long userId, Model model) {
        User profileUser = userService.findUserById(userId);
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("authenticatedUser", profileUser);
        return "profile";
    }

    @GetMapping("/editProfile/{userId}")
    public String editProfile(Principal principal, @PathVariable long userId, Model model) {
//        userService.identification(userId, principal);
        User profileUser = userService.findUserById(userId);
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("authenticatedUser", profileUser);
        return "editProfile";
    }

}
