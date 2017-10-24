package amigo.com.web;

import amigo.com.domain.PartyGuest;
import amigo.com.domain.PartyGuestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.ir.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.*;

/**
 * Created by Naver on 2017. 10. 18..
 */
@RestController
@Slf4j
public class ApiPartyGuestController {
    @Resource(name = "partyGuestRepository")
    private PartyGuestRepository partyGuestRepository;

    @GetMapping("/party/guest")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<PartyGuest> getParyGuests() {
        log.info("parties: {}", partyGuestRepository.findAll());
        return partyGuestRepository.findAll();
    }

    @PostMapping("/party/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public PartyGuest makePartyGuest(@RequestBody PartyGuest partyGuest) {
        PartyGuest savedPartyGuest = partyGuestRepository.save(partyGuest);
        log.info("saved party guest: {}", savedPartyGuest);
        return savedPartyGuest;
    }
}
