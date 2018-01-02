package com.amigotrip.repository;

import com.amigotrip.domain.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by NEXT on 2017. 12. 28..
 */
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
}
