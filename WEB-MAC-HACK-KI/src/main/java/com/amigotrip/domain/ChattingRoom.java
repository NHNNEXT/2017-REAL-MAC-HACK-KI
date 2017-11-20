package com.amigotrip.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Naver on 2017. 11. 9..
 */
@Entity
public class ChattingRoom {
    @Id
    private long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;
}
