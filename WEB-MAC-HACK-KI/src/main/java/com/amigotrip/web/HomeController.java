package com.amigotrip.web;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.repository.UserRepository;
import com.amigotrip.mail.AmigoMailSender;
import com.amigotrip.service.ArticleService;
import com.amigotrip.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/")
    public String main(Authentication principal, HttpSession session, Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (principal != null) {
            log.debug("login user: {}", principal.getName());
            model.addAttribute("authenticatedUser", userService.findUserByEmail(principal.getName()));
        }

        return "index";
    }

    @PostMapping("/search")
    public String list(String city, Model model) {
        List<LocalsArticle> localsArticleList = articleService.search(city);
        model.addAttribute("localsArticleList", localsArticleList);
        model.addAttribute("searchValue", city);
        return "list";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("localsArticle", articleService.findLocalsOne(1L));
        return "localsForm";
    }
}

