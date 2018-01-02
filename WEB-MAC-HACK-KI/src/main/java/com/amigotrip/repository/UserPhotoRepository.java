package com.amigotrip.repository;

import com.amigotrip.domain.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by NEXT on 2018. 1. 2..
 */
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
}
