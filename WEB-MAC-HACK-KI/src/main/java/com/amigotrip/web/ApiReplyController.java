package com.amigotrip.web;

import com.amigotrip.domain.*;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.ReplyRepository;
import com.amigotrip.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

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

    @PostMapping("/")
    public Reply createLocalArticleReply(@RequestBody Reply reply, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new BadRequestException();
        }

        reply.setWriter(user);
        Reply dbReply = replyRepository.save(reply);

        return dbReply;
    }

}
