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
 * Created by NEXT on 2017. 11. 16..
 */
@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LocalsArticle {

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
    private LocalDateTime createDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(LocalsArticle article) {
        this.photos = article.photos;
        this.contents = article.contents;
    }
}
