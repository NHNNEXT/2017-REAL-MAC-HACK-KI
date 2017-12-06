package com.amigotrip.web;

import com.amigotrip.domain.*;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by NEXT on 2017. 11. 30..
 */
@RestController
@Slf4j
@RequestMapping("/replies")
public class ApiReplyController {

    AtomicLong replyId = new AtomicLong(1);

    @Resource
    private UserRepository userRepository;

    @Resource
    private LocalsArticleRepository localsArticleRepository;

    @Resource
    private TravelerArticleRepository travelerArticleRepository;

    @Resource
    private LocalsReplyRepository localsReplyRepository;

    @Resource
    private TravelerReplyRepository travelerReplyRepository;

    @PostMapping("/locals/{articleId}")
    public LocalsReply createLocalArticleReply(@PathVariable Long articleId, @RequestBody LocalsReply reply, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        if (user == null) {
            throw new BadRequestException("Please login before write a reply.");
        }

        reply.setWriter(user);

        LocalsArticle dbArticle = localsArticleRepository.findOne(articleId);
        if (dbArticle == null) throw new BadRequestException("There is no such article");

        reply.setArticleId(dbArticle.getId());
        reply.setId(replyId.getAndAdd(1));


        log.debug("reply : {}", reply);

        LocalsReply dbReply = localsReplyRepository.save(reply);
        dbArticle.addReply(reply);
        localsArticleRepository.save(dbArticle);

        return dbReply;
    }

    @GetMapping("/locals/{articleId}")
    public List<LocalsReply> getLocalsArticleReplies(@PathVariable Long articleId) {
        return localsReplyRepository.findByArticleId(articleId);
    }

    @GetMapping("/locals/{articleId}/{replyId}")
    public LocalsReply getLocalsArticleReply(@PathVariable Long replyId) {
        LocalsReply dbReply = localsReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        return dbReply;
    }

    @PutMapping("/locals/{articleId}/{replyId}")
    public LocalsReply updateLocalsArticleReply(@PathVariable Long replyId, String contents, Principal principal) {
        LocalsReply dbReply = localsReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        if (!principal.getName().equals(dbReply.getWriter().getEmail())) throw new BadRequestException("Can not update a reply wrote by others");
        dbReply.update(contents);

        return localsReplyRepository.save(dbReply);
    }

    @DeleteMapping("/locals/{articleId}/{replyId}")
    public HttpStatus deleteLocalsArticleReply(@PathVariable Long articleId, @PathVariable Long replyId, Principal principal) {
        LocalsReply dbReply = localsReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");

        LocalsArticle dbArticle = localsArticleRepository.findOne(articleId);

        if (dbArticle == null) throw new BadRequestException("There is no such article"); // 글을 찾지 못한 경우

        if (dbReply.getWriter().getEmail().equals(principal.getName())) throw new BadRequestException("Can not delete a reply wrote by others"); // 로그인 사용자와 Reply의 writer가 일치하지 않는 경우

        dbArticle.deleteReply(dbReply); // article의 Set<LocalsReply> 에서 해당 reply 삭제

        localsArticleRepository.save(dbArticle);

        localsReplyRepository.delete(replyId);

        return HttpStatus.OK;
    }

    @PostMapping("/traveler/{articleId}")
    public TravelerReply createTravelerArticleReply(@PathVariable Long articleId, @RequestBody TravelerReply reply, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());

        if (user == null) {
            throw new BadRequestException("Please login before write a reply.");
        }

        reply.setWriter(user);

        TravelerArticle dbArticle = travelerArticleRepository.findOne(articleId);
        if (dbArticle == null) throw new BadRequestException("There is no such article");

        reply.setArticleId(dbArticle.getId());
        reply.setId(replyId.getAndAdd(1));

        TravelerReply dbReply = travelerReplyRepository.save(reply);
        dbArticle.addReply(reply);
        travelerArticleRepository.save(dbArticle);

        return dbReply;
    }

    @GetMapping("/traveler/{articleId}")
    public List<TravelerReply> getTravelerArticleReplies(@PathVariable Long articleId) {
        return travelerReplyRepository.findByArticleId(articleId);
    }

    @GetMapping("/traveler/{articleId}/{replyId}")
    public TravelerReply getTravelerArticleReply(@PathVariable Long replyId) {
        TravelerReply dbReply = travelerReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        return dbReply;
    }

    @PutMapping("/traveler/{articleId}/{replyId}")
    public TravelerReply updateTravelerArticleReply(@PathVariable Long replyId, String contents, Principal principal) {
        TravelerReply dbReply = travelerReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");
        if (!principal.getName().equals(dbReply.getWriter().getEmail())) throw new BadRequestException("Can not update a reply wrote by others");
        dbReply.update(contents);

        return travelerReplyRepository.save(dbReply);
    }

    @DeleteMapping("/traveler/{articleId}/{replyId}")
    public HttpStatus deleteTravelerArticleReply(@PathVariable Long articleId, @PathVariable Long replyId, Principal principal) {
        TravelerReply dbReply = travelerReplyRepository.findOne(replyId);
        if (dbReply == null) throw new BadRequestException("There is no such reply");

        TravelerArticle dbArticle = travelerArticleRepository.findOne(articleId);

        if (dbArticle == null) throw new BadRequestException("There is no such article");

        if (dbReply.getWriter().getEmail().equals(principal.getName())) throw new BadRequestException("Can not delete a reply wrote by others");

        dbArticle.deleteReply(dbReply);

        travelerArticleRepository.save(dbArticle);

        travelerArticleRepository.delete(replyId);

        return HttpStatus.OK;
    }
}
