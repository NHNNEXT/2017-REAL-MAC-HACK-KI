package com.amigotrip.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by NEXT on 2017. 11. 16..
 */
@Entity
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {

    @Id
    @Column(name = "reply_id")
    private long id;

    @JoinColumn(name = "article_id")
    private Long articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String contents;

    private LocalDateTime createDate;

    public void update(String contents) {
        this.contents = contents;
    }
}