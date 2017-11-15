package amigo.com.web;

import amigo.com.domain.RoleRepository;
import amigo.com.domain.User;
import amigo.com.domain.UserRepository;
import amigo.com.mail.AmigoMailSender;
import amigo.com.service.UserConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Created by Naver on 2017. 11. 8..
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class ApiUserController {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    AmigoMailSender amigoMailSender;

    @Resource(name = "service.userConfirmService")
    UserConfirmService userConfirmService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Result> createUser(@RequestBody User user, HttpServletResponse response) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<Result>(
                    new Result(),
                    HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
        }

        user.encryptionPassword(bCryptPasswordEncoder);
        user.addRole(roleRepository.findByRole("unconfirmed_user"));
        User savedUser = userRepository.save(user);
        amigoMailSender.sendEmailConfirmMail(savedUser);
        return new ResponseEntity<Result>(
                new Result("/user/" + savedUser.getId()),
                HttpStatus.valueOf(HttpStatus.CREATED.value()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable long userId) {
        if(!userConfirmService.isConfirmedUserByUserId(userId)) {
            return new ResponseEntity<User>(userRepository.findOne(userId),
                    HttpStatus.valueOf(HttpStatus.OK.value()));
        }
        return new ResponseEntity<User>(userRepository.findOne(userId),
                HttpStatus.valueOf(HttpStatus.ACCEPTED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody User user, HttpSession httpSession) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser == null) {
            return new ResponseEntity<Result>(new Result("/loginForm"), HttpStatus.BAD_REQUEST);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(findUser.getEmail(), findUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return new ResponseEntity<Result>(new Result("/"), HttpStatus.ACCEPTED);
    }
}
