package com.amigotrip.web;

import com.amigotrip.domain.PartyGuest;
import com.amigotrip.repository.PartyGuestRepository;
import com.amigotrip.mail.AmigoMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Naver on 2017. 10. 18..
 */
@RestController
@Slf4j
public class ApiPartyGuestController {
    @Resource(name = "amigoMailSender")
    public AmigoMailSender amigoMailSender;

    @Resource(name = "partyGuestRepository")
    private PartyGuestRepository partyGuestRepository;

    @GetMapping("/party/guest")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<PartyGuest> getParyGuests() {
        log.debug("parties: {}", partyGuestRepository.findAll());
        return partyGuestRepository.findAll();
    }

    @PostMapping("/party/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public PartyGuest makePartyGuest(@RequestBody PartyGuest partyGuest) {
        PartyGuest savedPartyGuest = partyGuestRepository.save(partyGuest);
        log.info("saved party guest: {}", savedPartyGuest);
        amigoMailSender.sendPartyGuestFormMail(savedPartyGuest);
        return savedPartyGuest;
    }
}
