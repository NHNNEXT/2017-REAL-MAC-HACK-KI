package hubomoa.com.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Naver on 2017. 10. 9..
 */
@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("HOME");
        return "index";
    }
}
