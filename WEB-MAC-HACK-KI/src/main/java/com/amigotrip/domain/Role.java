package com.amigotrip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Naver on 2017. 11. 12..
 */
@ToString
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    @Getter
    @Setter
    private long id;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        return role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
