package com.amigotrip.service;

import com.amigotrip.domain.RoleRepository;
import com.amigotrip.domain.User;
import com.amigotrip.domain.UserRepository;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.mail.AmigoMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Naver on 2017. 11. 26..
 */
@Slf4j
@Service("service.userService")
@ComponentScan({"com.amigotrip.domain", "com.amigotrip.web.config"})
public class UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private AmigoMailSender amigoMailSender;

    @Resource
    private AuthenticationManager authenticationManager;


    public boolean isDuplicatedEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User signup(User user) {
        user.encryptionPassword(bCryptPasswordEncoder);
        user.addRole(roleRepository.findByRole("unconfirmed_user"));
        User savedUser = userRepository.save(user);
        amigoMailSender.sendEmailConfirmMail(savedUser);
        return savedUser;
    }

    public User login(User user, HttpSession httpSession) {
        checkValidateLogin(user);
        User dbUser = userRepository.findByEmail(user.getEmail());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(dbUser.getEmail(), dbUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return dbUser;
    }

    public String confirmUserByUserId(long userId, String key) {
        User user = userRepository.findOne(userId);
        if (user == null || !user.getEmailConfirmKey().equals(key)) {
            throw new BadRequestException();
        }

        user.addRole(roleRepository.findByRole("user"));
        user.removeRole(roleRepository.findByRole("unconfirmed_user"));
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    public boolean isConfirmedUserId(long id) {
        return userRepository.findOne(id).getRoles().contains(roleRepository.findByRole("user"));
    }

    public User findUserById(long id) {
        return userRepository.findOne(id);
    }

    public void checkValidateLogin(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser == null) {
            throw new BadRequestException("Email is wrong! Please check again.");
        }

        if (!dbUser.isSamePassword(user.getPassword(), bCryptPasswordEncoder)) {
            throw new BadRequestException("Password is wrong! Please check again");
        }
    }
}
