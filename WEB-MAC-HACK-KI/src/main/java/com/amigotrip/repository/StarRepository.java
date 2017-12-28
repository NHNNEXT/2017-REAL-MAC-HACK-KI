package com.amigotrip.repository;

import com.amigotrip.domain.Star;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Woohyeon on 2017. 12. 28..
 */
public interface StarRepository extends CrudRepository<Star, Long> {
}
