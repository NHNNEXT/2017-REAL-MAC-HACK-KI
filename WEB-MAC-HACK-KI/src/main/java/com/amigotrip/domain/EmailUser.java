package com.amigotrip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Email")
@Data
@NoArgsConstructor
public class EmailUser  extends User{
    public EmailUser(String email, String password, String name) {
        super(email, password, name);
    }
}
