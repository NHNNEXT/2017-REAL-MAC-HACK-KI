package com.amigotrip.web;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.repository.UserRepository;
import com.amigotrip.mail.AmigoMailSender;
import com.amigotrip.service.ArticleService;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.security.Principal;
import java.util.List;


/**
 * Created by Naver on 2017. 10. 16..
 */
@Controller
@Slf4j
public class HomeController {
    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public HomeController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }


    @GetMapping("/")
    public String main(Principal principal) {
        if(connectionRepository.findPrimaryConnection(Facebook.class) != null) {
            log.debug("{}", facebook.userOperations().getUserProfile());
        }
        log.debug("principal: {}", principal);
        return "index";
    }

    @GetMapping("/list")
    public String list(Principal principal) {
        return "list";
    }

    @PostMapping("/search")
    public String list(String city, Model model) {
        List<LocalsArticle> localsArticleList = articleService.search(city);
        model.addAttribute("localsArticleList", localsArticleList);
        log.debug("CITY: {}", localsArticleList);
        return "list";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/test")
    public String test() {
        return "editProfile";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/profile";
    }
}

