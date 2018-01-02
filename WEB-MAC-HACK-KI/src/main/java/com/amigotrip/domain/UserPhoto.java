package com.amigotrip.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto {
    @Id @GeneratedValue
    @Column(name = "user_photo_id")
    private long userPhotoId;

//    private User writer;

    private String name;

    public UserPhoto (String name) {
        this.name = name;
    }
}
