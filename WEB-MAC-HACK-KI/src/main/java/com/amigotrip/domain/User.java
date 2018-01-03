package com.amigotrip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Naver on 2017. 11. 8..
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Data
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @JsonIgnore
    private String password;

    private String gender;

    private String nationality;

    private String city;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id")
    private Collection<UserPhoto> userPhotos;

    @JsonIgnore
    @Column(nullable = true)
    private Integer creditPoint;

    private Date birthday;

    @Lob
    @Column(name = "contents", length = 512)
    private String contents;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(name = "to_id")
    private Set<Review> reviews;

    @OneToMany
    @JoinColumn(name = "to_id")
    private Set<Star> stars;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Language> languages;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userInterest", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "interestId"))
    private Set<Interest> interests;



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

    public boolean isSamePassword(String password, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return bCryptPasswordEncoder.matches(password, this.password);
    }

    public void updateUser(User user) {
        if(user.gender != null) {
            this.gender = user.gender;
        }

        if(user.birthday != null) {
            this.birthday = user.birthday;
        }

        if(user.nationality != null) {
            this.nationality = user.nationality;
        }

        if(user.city != null) {
            this.city = user.city;
        }

        if(user.contents != null) {
            this.contents = user.contents;
        }
    }

    public Collection<UserPhoto> getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhoto(List<UserPhoto> userPhotos) {
        this.userPhotos = userPhotos;
    }

    public void addUserPhoto(UserPhoto p) {
        if (userPhotos == null) {
            userPhotos = new ArrayList<UserPhoto>();
        }
        userPhotos.add(p);
    }

    public int countStars() {
        return stars.size();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", city='" + city + '\'' +
                ", creditPoint=" + creditPoint +
                ", birthday=" + birthday +
                ", contents='" + contents + '\'' +
                ", roles=" + roles +
                ", reviews=" + reviews +
                ", stars=" + stars +
                ", languages=" + languages +
                ", interests=" + interests +
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
