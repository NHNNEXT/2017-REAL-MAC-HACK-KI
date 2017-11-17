package com.amigotrip.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Reply {


    @Id
    @Column
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String contents;

    private LocalDateTime createDate;
}
