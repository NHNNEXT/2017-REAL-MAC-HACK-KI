package com.amigotrip.repository;

import com.amigotrip.domain.PartyGuest;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Naver on 2017. 10. 18..
 */
public interface PartyGuestRepository extends CrudRepository<PartyGuest, Long> {
}
