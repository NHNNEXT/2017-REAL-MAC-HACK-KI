package com.amigotrip.web;

import com.amigotrip.domain.ChattingRoom;
import com.amigotrip.domain.User;
import com.amigotrip.exception.BadRequestException;
import com.amigotrip.repository.ChattingRoomRepository;
import com.amigotrip.repository.UserRepository;
import com.amigotrip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by NEXT on 2018. 1. 4..
 */
@RestController
@Slf4j
@RequestMapping("/chat")
public class ApiChattingRoomController {

    @Resource
    private ChattingRoomRepository chattingRoomRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    @GetMapping("/{roomId}")
    public ChattingRoom getChattingRoom(@PathVariable long roomId, Principal principal) {
        userService.checkLogined(principal);
        return chattingRoomRepository.findOne(roomId);
    }

    @PostMapping("/{partnerId}")
    public ChattingRoom makeChattingRoom(@PathVariable long partnerId, Principal principal) {
        userService.checkLogined(principal);
        User loginedUser = userService.findUserByEmail(principal.getName());
        User partner = userService.findUserById(partnerId);
        if (partner == null) throw new BadRequestException("There's no such user.");
        List<ChattingRoom> myChattingrooms = chattingRoomRepository.findByUsers_Id(loginedUser.getId());
        List<ChattingRoom> partnerChattingrooms = chattingRoomRepository.findByUsers_Id(partnerId);

        for (ChattingRoom c : myChattingrooms) {
            if (partnerChattingrooms.contains(c)) {
                return c;
            }
        }

        ChattingRoom cRoom = new ChattingRoom();
        cRoom.addUsers(loginedUser);
        cRoom.addUsers(partner);
        ChattingRoom dbCRoom = chattingRoomRepository.save(cRoom);
        loginedUser.addChattingRoom(dbCRoom);
        userRepository.save(loginedUser);
        partner.addChattingRoom(dbCRoom);
        userRepository.save(partner);

        return dbCRoom;
    }

    @GetMapping("/rooms/{userId}")
    public List<ChattingRoom> getChattingRoomsByUserId(@PathVariable long userId, Principal principal) {
        userService.identification(userId, principal);
        return chattingRoomRepository.findByUsers_Id(userId);
    }
}
