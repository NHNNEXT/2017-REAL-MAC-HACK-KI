package amigo.com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Naver on 2017. 10. 16..
 */
@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}

