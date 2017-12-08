package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long photoId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String name;

    public Photo (String name) {
        this.name = name;
    }
}
