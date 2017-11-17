package com.amigotrip.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Star {
    @EmbeddedId
    StarId id;
}

@Embeddable
class StarId implements Serializable{
    @NotEmpty
    @Column(name = "from_id")
    long fromId;

    @NotEmpty
    @Column(name = "to_id")
    long toId;
}