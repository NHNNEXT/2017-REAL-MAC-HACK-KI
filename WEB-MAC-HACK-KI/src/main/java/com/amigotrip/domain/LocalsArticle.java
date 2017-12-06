package com.amigotrip.domain;

import lombok.*;
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
@Data
public class LocalsArticle {

    @Id
    @Column(name = "locals_article_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany
    @JoinColumn(name = "photo_id")
    private Set<Photo> photos;

    @OneToMany
    @JoinColumn(name = "locals_reply_id")
    private Set<LocalsReply> replies;

    private String location;

    private String contents;

    private LocalDateTime createDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(LocalsArticle article) {
        this.photos = article.photos;
        this.contents = article.contents;
    }

    public void addReply(LocalsReply reply) {
        replies.add(reply);
    }

    public void deleteReply(LocalsReply reply) {
        replies.remove(reply);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}
