package amigo.com.web;

import amigo.com.domain.User;
import amigo.com.domain.UserRepository;
import amigo.com.mail.AmigoMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.mail.MessagingException;


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

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/user/{userId}/emailConfirm/{key}")
    public String mail(@PathVariable long userId, @PathVariable String key) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return "redirect:/";
        }

        if(user.getEmailConfirmKey().equals(key)) {
            user.confirmUser();
            userRepository.save(user);
            return "redirect:/loginForm";
        }
        return "redirect:/";
    }
}

