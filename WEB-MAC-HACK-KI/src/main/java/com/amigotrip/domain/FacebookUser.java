package com.amigotrip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("Facebook")
public class FacebookUser extends User {

    public FacebookUser(String email, String password, String name) {
        super(email, password, name);
    }
}
