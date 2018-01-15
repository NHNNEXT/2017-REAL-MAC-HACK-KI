package com.amigotrip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("Google")
public class GoogleUser extends User {
    public GoogleUser(String email, String password, String name) {
        super(email, password, name);
    }
}
