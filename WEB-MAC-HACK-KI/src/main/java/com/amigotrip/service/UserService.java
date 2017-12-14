package com.amigotrip.service;

import com.amigotrip.repository.RoleRepository;
import com.amigotrip.domain.User;
import com.amigotrip.repository.UserRepository;
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
import java.security.Principal;

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

    public boolean isLogined(Principal principal) {
        if (userRepository.findByEmail(principal.getName()) == null) return false;
        return true;
    }

    public User signup(String email, String password, String name) {
        User user = new User(email, password, name);
        user.encryptionPassword(bCryptPasswordEncoder);
        user.addRole(roleRepository.findByRole("unconfirmed_user"));
        User savedUser = userRepository.save(user);
        amigoMailSender.sendEmailConfirmMail(savedUser);
        return savedUser;
    }

    public User login(String email, String password, HttpSession httpSession) {
        checkValidateLogin(email, password);
        User dbUser = userRepository.findByEmail(email);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(dbUser.getEmail(), dbUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return dbUser;
    }

    public User updateUser(long id, User user) {
        User dbUser = userRepository.findOne(id);
        dbUser.updateUser(user);
        userRepository.save(dbUser);
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
        return "confirmed";
    }

    public boolean isConfirmedUserId(long id) {
        return userRepository.findOne(id).getRoles().contains(roleRepository.findByRole("user"));
    }

    public User findUserById(long id) {
        return userRepository.findOne(id);
    }

    public void checkValidateLogin(String email, String password) {
        User dbUser = userRepository.findByEmail(email);
        if (dbUser == null) {
            throw new BadRequestException("Email is wrong! Please check again.");
        }

        if (!dbUser.isSamePassword(password, bCryptPasswordEncoder)) {
            throw new BadRequestException("Password is wrong! Please check again");
        }
    }
}
