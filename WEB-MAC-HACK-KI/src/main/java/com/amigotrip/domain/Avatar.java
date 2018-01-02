package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by NEXT on 2018. 1. 2..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avatar { // user profile image domain
    @Id @GeneratedValue
    @Column(name = "avatar_id")
    private long avatarId;

    private String path;
}
