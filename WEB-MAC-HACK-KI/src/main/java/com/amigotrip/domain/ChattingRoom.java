package com.amigotrip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Naver on 2017. 11. 9..
 */
@Entity
@Data
public class ChattingRoom {
    @Id @GeneratedValue
    @Column(name = "chattingroom_id")
    private long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "chattingRooms")
    private Set<User> users;

    public void addUsers(User u) {
        if (users == null) {
            users = new HashSet<User>();
        }
        users.add(u);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChattingRoom that = (ChattingRoom) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
