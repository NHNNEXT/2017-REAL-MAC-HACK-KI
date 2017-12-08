package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by NEXT on 2017. 12. 6..
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalsReply {

    @Id
    @Column(name = "locals_reply_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JoinColumn(name = "locals_article_id")
    private long articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String contents;

    private LocalDateTime createDate;

    public void update(String contents) {
        this.contents = contents;
    }
}
