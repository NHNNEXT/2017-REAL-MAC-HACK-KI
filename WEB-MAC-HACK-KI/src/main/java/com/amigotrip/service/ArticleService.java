package com.amigotrip.service;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.domain.User;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.LocalsArticleRepository;
import com.amigotrip.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Slf4j
@Service("service.articleService")
@ComponentScan({"com.amigotrip.domain", "com.amigotrip.web.config"})
public class ArticleService {
    @Resource
    private LocalsArticleRepository localsArticleRepository;

    @Resource
    private UserRepository userRepository;

    public List<LocalsArticle> search(String city) {
        return localsArticleRepository.findByLocationLike("%" + city + "%");
    }

    public List<LocalsArticle> findLocalsAll() { return localsArticleRepository.findAll(); }

    public LocalsArticle findLocalsOne(long articleId) {
        return localsArticleRepository.findOne(articleId);
    }

    public LocalsArticle createLocalArticle(Principal principal, LocalsArticle localsArticle) {
        User sessionUser = getSessionUser(principal);
        localsArticle.setWriter(sessionUser);
        return localsArticleRepository.save(localsArticle);
    }

    public LocalsArticle updateLocalsArticle(Principal principal, long id, LocalsArticle localsArticle) {
        if(principal == null) {
            throw new BadRequestException("Please login");
        }
        User loginUser = userRepository.findByEmail(principal.getName());
        LocalsArticle dbArticle = localsArticleRepository.findOne(id);
        if(!dbArticle.isAcceptedUser(loginUser)) {
            throw new BadRequestException("You can edit only you written your self");
        }
        dbArticle.updateArticle(localsArticle);
        return localsArticleRepository.save(dbArticle);
    }

    public void deleteLocalsArticle(Principal principal, long id) {
        if(principal == null) {
            throw new BadRequestException("Please login");
        }
        User loginUser = userRepository.findByEmail(principal.getName());
        LocalsArticle dbArticle = localsArticleRepository.findOne(id);
        if(!dbArticle.isAcceptedUser(loginUser)) {
            throw new BadRequestException("You can edit only you written your self");
        }
        localsArticleRepository.delete(dbArticle.getId());
    }

    public User getSessionUser(Principal principal) {
        if(principal == null) {
            throw new BadRequestException("login please");
        }
        return userRepository.findByEmail(principal.getName());
    }
}