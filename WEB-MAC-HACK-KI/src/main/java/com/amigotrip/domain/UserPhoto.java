package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by NEXT on 2018. 1. 2..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto {
    @Id
    @Column(name = "user_photo_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userPhotoId;

    @Column(name="owner_id")
    private long ownerId;
}
