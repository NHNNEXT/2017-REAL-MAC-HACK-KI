package com.amigotrip.web;

import com.amigotrip.repository.UserRepository;
import com.amigotrip.mail.AmigoMailSender;
import com.amigotrip.service.UserService;
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
    public UserService userService;

    @GetMapping("/")
    public String main(Principal principal) {
        log.debug("principal: {}", principal);
        return "index";
    }

    @GetMapping("/list")
    public String list(Principal principal) {
        return "list";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/test")
    public String test() {
        return "confirmed";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/profile";
    }
}

