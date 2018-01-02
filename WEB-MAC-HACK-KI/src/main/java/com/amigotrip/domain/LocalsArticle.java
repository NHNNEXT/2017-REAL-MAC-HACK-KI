package com.amigotrip.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by NEXT on 2017. 11. 16..
 */
@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class LocalsArticle {

    @Id
    @Column(name = "locals_article_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="article_id")
    private Collection<Photo> photos;

    @OneToMany
    @JoinColumn(name = "locals_reply_id")
    private Set<LocalsReply> replies;

    private String location;

    private String contents;

    @CreatedDate
    private LocalDateTime createDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(LocalsArticle article) {
        this.title = article.title;
        this.location = article.location;
        this.contents = article.contents;
    }

    public boolean isAcceptedUser(User user) {
        return user.equals(writer);
    }

    public void addReply(LocalsReply reply) {
        replies.add(reply);
    }

    public void deleteReply(LocalsReply reply) {
        replies.remove(reply);
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto(Photo p) {
        if (photos == null) {
            photos = new ArrayList<Photo>();
        }
        photos.add(p);
    }
}
