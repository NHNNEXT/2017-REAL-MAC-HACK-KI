package com.amigotrip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Naver on 2017. 11. 8..
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long id;

    @NotEmpty
    @Getter
    @Setter
    private String name;

    @NotEmpty
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @JsonIgnore
    private String password;

    private String gender;

    private int age = 0; // data.sql에서 이 값을 주지 않고 insert 하면 NULL not allowed for column "AGE" 라는 에러가 발생함

    private String nationality;

    private String city;

    @JsonIgnore
    private int creditPoint;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Photo profileImg;

    private String contents;

    @Getter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(name = "writer_id")
    private Set<Photo> photos;

    @OneToMany
    @JoinColumn(name = "to_id")
    private Set<Review> reviews;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void encryptionPassword(BCryptPasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void addRole(Role role) {
        roles = new HashSet<>();
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    @JsonIgnore
    public String getEmailConfirmKey() {
        String key = "";
        char character;

        for(int i = 0 ; i < password.length() ; i++) {
            character = password.charAt(i);
            if( character > '1') {
                key += character;
            }
        }
        return key;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
