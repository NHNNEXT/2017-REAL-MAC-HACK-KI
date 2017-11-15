package amigo.com.service;

import amigo.com.domain.RoleRepository;
import amigo.com.domain.User;
import amigo.com.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Naver on 2017. 11. 15..
 */
@Slf4j
@Service("service.userConfirmService")
public class UserConfirmService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    public boolean isConfirmedUserByUserId(long userId) {
        User user = userRepository.findOne(userId);
        return user.getRoles().contains(roleRepository.findByRole("user"));
    }

    public String confirmUserByUserId(long userId, String key) {
        User user = userRepository.findOne(userId);
        if(user == null) {
            return "redirect:/";
        }

        if(!isConfirmedUserByUserId(userId) && user.getEmailConfirmKey().equals(key)) {
            user.addRole(roleRepository.findByRole("user"));
            user.removeRole(roleRepository.findByRole("unconfirmed_user"));
            userRepository.save(user);
            return "redirect:/loginForm";
        }
        return "redirect:/";
    }
}
