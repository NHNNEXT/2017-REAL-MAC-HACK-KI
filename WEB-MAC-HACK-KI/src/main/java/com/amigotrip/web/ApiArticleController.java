package com.amigotrip.web;

import com.amigotrip.domain.*;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.*;
import com.amigotrip.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by NEXT on 2017. 11. 16..
 */
@RestController
@Slf4j
@RequestMapping("/articles")
public class ApiArticleController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ArticleService articleService;

    @Resource
    private LocalsArticleRepository localsArticleRepository;

    @Resource
    private TravelerArticleRepository travelerArticleRepository;

    @Resource
    private LocalsReplyRepository localsReplyRepository;

    @Resource
    private TravelerReplyRepository travelerReplyRepository;

    @Resource
    private PhotoRepository photoRepository;

    @GetMapping("/locals")
    public List<LocalsArticle> getLocalsArticleList() {
        List<LocalsArticle> list = localsArticleRepository.findAll();
        return list;
    }

    @GetMapping("/locals/{id}")
    public LocalsArticle getLocalsArticle(@PathVariable long id) {
        LocalsArticle article = localsArticleRepository.findOne(id);
        if (article == null) throw new BadRequestException("No LocalArticle Exists");
        return article;
    }

    @PostMapping("/locals")
    @ResponseStatus(HttpStatus.CREATED)
    public LocalsArticle postLocalsArticle(@RequestBody LocalsArticle article, Principal principal) {
        log.debug("LOCAL PRINCIPAL: {}", principal);
        return articleService.createLocalArticle(principal, article);
    }

    @PutMapping("/locals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocalsArticle updateLocalsArticle(@PathVariable Long id, @RequestBody LocalsArticle article, Principal principal) {
        return articleService.updateLocalsArticle(principal, id, article);
    }

    @DeleteMapping("/locals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteLocalsArticle(@PathVariable Long id, Principal principal) {
        articleService.deleteLocalsArticle(principal, id);
        return new Result();
    }

    @GetMapping("/traveler")
    public List<TravelerArticle> getTravelerArticleList() {
        List<TravelerArticle> list = travelerArticleRepository.findAll();

        return list;
    }

    @GetMapping("/traveler/{id}")
    public TravelerArticle getTravelerArticle(@PathVariable long id) {
        TravelerArticle article = travelerArticleRepository.findOne(id);
        if (article == null) throw new BadRequestException("No Article Exists");
        return article;
    }

    @PostMapping("/traveler")
    public TravelerArticle postTravelerArticle(@RequestBody TravelerArticle article, Principal principal) {
        if (principal == null) throw new BadRequestException("Login first before writing an article");
        User writer = userRepository.findByEmail(principal.getName());

        article.setWriter(writer);

        return travelerArticleRepository.save(article);
    }

    @PutMapping("/traveler/{id}")
    public ResponseEntity<Result> updateTravelerArticle(@PathVariable Long id, @RequestBody TravelerArticle article, Principal principal) {
        TravelerArticle dbArticle = travelerArticleRepository.findOne(id);
        if (dbArticle == null) {
            return new ResponseEntity<Result>(
                    new Result("/articles/traveler"),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (principal == null) {
            return new ResponseEntity<Result>(
                    new Result("/loginForm"),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        dbArticle.updateArticle(article);
        travelerArticleRepository.save(dbArticle);
        return new ResponseEntity<Result>(
                new Result("/articles/traveler"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/traveler/{id}")
    public ResponseEntity<Result> deleteTravelerArticle(@PathVariable Long id, Principal principal) {
        TravelerArticle article = travelerArticleRepository.findOne(id);
        if (article == null) throw new BadRequestException("There is no such article");
        if (!article.getWriter().getEmail().equals(principal.getName())) throw new BadRequestException("Can not delete an article wrote by others");

        for (TravelerReply r : article.getReplies()) { // delete replies as well
            travelerReplyRepository.delete(r.getId());
        }
        travelerArticleRepository.delete(id);
        return new ResponseEntity<Result>(
                new Result("/articles/traveler"),
                HttpStatus.OK
        );
    }
}
