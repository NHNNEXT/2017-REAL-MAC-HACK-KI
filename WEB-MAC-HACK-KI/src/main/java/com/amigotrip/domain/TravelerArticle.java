package com.amigotrip.domain;

import lombok.*;
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
@Data
public class TravelerArticle implements Article {

    @Id
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany
    @JoinColumn(name = "reply_id")
    private Set<Reply> replies;

    private String location;

    private String contents;

    private LocalDateTime createDate;

    private String beginDate;

    private String endDate;

    public boolean isMatchWriter(User writer) {
        return this.writer == writer;
    }

    public void updateArticle(Article article) {
        TravelerArticle tArticle = (TravelerArticle)article;
        this.beginDate = tArticle.beginDate;
        this.endDate = tArticle.endDate;
        this.contents = tArticle.contents;
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }

    public void deleteReply(Reply reply) {
        replies.remove(reply);
    }
}
