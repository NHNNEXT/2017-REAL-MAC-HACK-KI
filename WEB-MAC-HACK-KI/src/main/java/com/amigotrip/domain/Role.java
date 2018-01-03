package com.amigotrip.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Naver on 2017. 11. 12..
 */
@ToString
@Entity
@NoArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role")
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
