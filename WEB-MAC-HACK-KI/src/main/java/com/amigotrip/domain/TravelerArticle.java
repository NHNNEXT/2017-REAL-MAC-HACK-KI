package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by NEXT on 2017. 11. 15..
 */
@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TravelerArticle {

    @Id
    @Column
    @Getter
    @Setter
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User writer;

    @OneToMany
    @JoinColumn(name = "photo_id")
    @Getter
    @Setter
    private Set<Photo> photos;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private String contents;

    @Getter
    @Setter
    private String beginDate;

    @Getter
    @Setter
    private String endDate;

    @Getter
    @Setter
    private LocalDateTime createDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(TravelerArticle article) {
        this.photos = article.photos;
        this.beginDate = article.beginDate;
        this.endDate = article.endDate;
        this.contents = article.contents;
    }
}
