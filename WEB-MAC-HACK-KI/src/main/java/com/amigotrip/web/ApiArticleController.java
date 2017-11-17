package com.amigotrip.web;

import com.amigotrip.domain.*;
import com.amigotrip.domain.*;
import lombok.extern.slf4j.Slf4j;
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

    AtomicLong articleId = new AtomicLong(1);

    @Resource
    private UserRepository userRepository;

    @Resource
    private LocalsArticleRepository localsArticleRepository;

    @Resource
    private TravelerArticleRepository travelerArticleRepository;

    @GetMapping("/locals")
    public List<LocalsArticle> getLocalsArticleList() {
        List<LocalsArticle> list = localsArticleRepository.findAll();

        return list;
    }

    @PostMapping("/locals")
    public ResponseEntity<Result> postLocalsArticle(@RequestBody LocalsArticle article, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<Result>(
                    new Result("/loginForm"),
                    HttpStatus.NOT_ACCEPTABLE
                    );
        }
        User writer = userRepository.findByEmail(principal.getName());

        article.setId(articleId.getAndAdd(1));
        article.setWriter(writer);
        localsArticleRepository.save(article);

        return new ResponseEntity<Result>(
                new Result("/articles/locals"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/locals/{id}")
    public ResponseEntity<Result> updateLocalsArticle(@PathVariable Long id, @RequestBody LocalsArticle article, Principal principal) {
        LocalsArticle dbArticle = localsArticleRepository.findOne(id);
        if (dbArticle == null) {
            return new ResponseEntity<Result>(
                    new Result("/articles/locals"),
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
        localsArticleRepository.save(dbArticle);
        return new ResponseEntity<Result>(
                new Result("/articles/locals"),
                HttpStatus.OK
        );
    }

    @GetMapping("/traveler")
    public List<TravelerArticle> getTravelerArticleList() {
        List<TravelerArticle> list = travelerArticleRepository.findAll();

        return list;
    }

    @PostMapping("/traveler")
    public ResponseEntity<Result> postTravelerArticle(@RequestBody TravelerArticle article, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<Result>(
                    new Result("/loginForm"),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        User writer = userRepository.findByEmail(principal.getName());

        article.setId(articleId.getAndAdd(1));
        article.setWriter(writer);
        travelerArticleRepository.save(article);

        return new ResponseEntity<Result>(
                new Result("/articles/traveler"),
                HttpStatus.CREATED
        );
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
}
