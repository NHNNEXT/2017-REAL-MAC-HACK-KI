package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Naver on 2017. 11. 9..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @Column(name = "photo_id")
    private long photoId;

    @Column(name = "writer_id")
    private long writerId;

    private String name;

    public Photo (String name) {
        this.name = name;
    }
}
