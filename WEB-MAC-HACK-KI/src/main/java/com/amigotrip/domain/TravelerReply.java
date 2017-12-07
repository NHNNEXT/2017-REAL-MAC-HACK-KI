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
public class TravelerReply {

    @Id
    @Column(name = "traveler_reply_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JoinColumn(name = "traveler_article_id")
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
