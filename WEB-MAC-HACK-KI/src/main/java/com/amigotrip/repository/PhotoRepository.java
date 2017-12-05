package com.amigotrip.repository;

import com.amigotrip.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by NEXT on 2017. 12. 5..
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
