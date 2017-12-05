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
public class LocalsArticle implements Article {

    @Id
    @Column(name = "article_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany
    @JoinColumn(name = "photo_id")
    private Set<Photo> photos;

    @OneToMany
    @JoinColumn(name = "reply_id")
    private Set<Reply> replies;

    private String location;

    private String contents;

    private LocalDateTime createDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(Article article) {
        LocalsArticle lArticle = (LocalsArticle)article; // 받을 때 Article 형으로 받은 Entity 형변환
        this.photos = lArticle.photos;
        this.contents = lArticle.contents;
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }

    public void deleteReply(Reply reply) {
        replies.remove(reply);
    }
}
