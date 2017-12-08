package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto {
    @Id
    @Column(name = "user_photo_id")
    private long userPhotoId;

    @Column(name = "writer_id")
    private long writerId;

    private String name;

    public UserPhoto (String name) {
        this.name = name;
    }
}
