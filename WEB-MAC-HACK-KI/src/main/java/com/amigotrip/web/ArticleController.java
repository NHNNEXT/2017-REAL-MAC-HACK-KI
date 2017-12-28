package com.amigotrip.web;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.service.ArticleService;
import com.amigotrip.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Created by Woohyeon on 2017. 12. 27..
 */
@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Resource
    private UserService userService;

    @GetMapping("/localsDetail/{articleId}")
    public String getLocalsArticle(Principal principal, @PathVariable long articleId, Model model) {
        model.addAttribute("localsArticle", articleService.findLocalsOne(articleId));
        if (principal != null) {
            model.addAttribute("authenticatedUser", userService.findUserByEmail(principal.getName()));
        }
        return "localsArticle";
    }

    @GetMapping("/localsForm/{articleId}")
    public String editLocalsArticle(Principal principal, Model model, @PathVariable long articleId) {
        LocalsArticle localsArticle = articleService.findLocalsOne(articleId);
        model.addAttribute("localsArticle", localsArticle);
        if (principal != null) {
            model.addAttribute("authenticatedUser", userService.findUserByEmail(principal.getName()));
        }
        return "localsEditForm";
    }

    @GetMapping("/localsForm")
    public String localsArticleForm(Principal principal, Model model) {
        if(principal == null) {
            return "/";
        }
        if (principal != null) {
            model.addAttribute("authenticatedUser", userService.findUserByEmail(principal.getName()));
        }
        return "localsForm";
    }
}