package com.amigotrip.web;

import com.amigotrip.domain.*;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.LocalsArticleRepository;
import com.amigotrip.repository.ReplyRepository;
import com.amigotrip.repository.TravelerArticleRepository;
import com.amigotrip.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * Created by NEXT on 2017. 11. 30..
 */
@RestController
@Slf4j
@RequestMapping("/replies")
public class ApiReplyController {
    @Resource
    private ReplyRepository replyRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private LocalsArticleRepository localsArticleRepository;

    @Resource
    private TravelerArticleRepository travelerArticleRepository;

    @PostMapping("/{article_id}")
    public Reply createLocalArticleReply(@PathVariable Long article_id, @RequestBody Reply reply, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        if (user == null) {
            throw new BadRequestException("Please login before write a reply.");
        }

        reply.setWriter(user);

        Article dbArticle = localsArticleRepository.findOne(article_id);
        if (dbArticle == null) travelerArticleRepository.findOne(article_id);
        if (dbArticle == null) throw new BadRequestException("There is no such article");

        Reply dbReply = replyRepository.save(reply);
        dbArticle.addReply(reply);
        if (dbArticle.getClass().getName().equals("com.amigotrip.domain.LocalsArticle")) localsArticleRepository.save((LocalsArticle)dbArticle);
        else travelerArticleRepository.save((TravelerArticle)dbArticle);

        return dbReply;
    }

    @GetMapping("/{article_id}")
    public List<Reply> getArticleReplies(@PathVariable Long article_id) {
        return replyRepository.findByArticleId(article_id);
    }

    @GetMapping("/{article_id}/{reply_id}")
    public Reply getArticleReply(@PathVariable Long reply_id) {
        Reply dbReply = replyRepository.findOne(reply_id);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        return dbReply;
    }

    @PutMapping("/{article_id}/{reply_id}")
    public Reply updateArticleReply(@PathVariable Long reply_id, String contents, Principal principal) {
        Reply dbReply = replyRepository.findOne(reply_id);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        if (!principal.getName().equals(dbReply.getWriter().getEmail())) throw new BadRequestException("Can not update a reply wrote by others");
        dbReply.update(contents);

        return replyRepository.save(dbReply);
    }

    @DeleteMapping("/{article_id}/{reply_id}")
    public HttpStatus deleteArticleReply(@PathVariable Long article_id, @PathVariable Long reply_id, Principal principal) {
        Reply dbReply = replyRepository.findOne(reply_id);
        if (dbReply == null) throw new BadRequestException("There is no such reply");

        Article dbArticle = localsArticleRepository.findOne(article_id);
        if (dbArticle == null) dbArticle = travelerArticleRepository.findOne(article_id); // LocalsArticle 에서 못 찾으면 TravelerArticle 중에서 찾기

        if (dbArticle == null) throw new BadRequestException("There is no such article"); // 글을 찾지 못한 경우

        if (dbReply.getWriter().getEmail().equals(principal.getName())) throw new BadRequestException("Can not delete a reply wrote by others"); // 로그인 사용자와 Reply의 writer가 일치하지 않는 경우

        dbArticle.deleteReply(dbReply); // article의 Set<Reply> 에서 해당 reply 삭제

        if (dbArticle.getClass().getName().equals("com.amigotrip.domain.LocalsArticle")) localsArticleRepository.save((LocalsArticle)dbArticle);
        else travelerArticleRepository.save((TravelerArticle)dbArticle); // Article 종류에 따라 맞는 repository에 update

        replyRepository.delete(reply_id);

        return HttpStatus.OK;
    }
}
