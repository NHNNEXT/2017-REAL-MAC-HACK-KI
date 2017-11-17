package com.amigotrip.web;

import com.amigotrip.domain.UserRepository;
import com.amigotrip.mail.AmigoMailSender;
import com.amigotrip.service.UserConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.security.Principal;


/**
 * Created by Naver on 2017. 10. 16..
 */
@Controller
@Slf4j
public class HomeController {
    @Resource
    public AmigoMailSender amigoMailSender;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @Resource
    public UserRepository userRepository;

    @Resource(name = "service.userConfirmService")
    public UserConfirmService userConfirmService;

    @GetMapping("/")
    public String home(Principal principal) {
        log.debug("principal: {}", principal);
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/user/{userId}/emailConfirm/{key}")
    public String mail(@PathVariable long userId, @PathVariable String key) {
        return userConfirmService.confirmUserByUserId(userId, key);
    }

}

