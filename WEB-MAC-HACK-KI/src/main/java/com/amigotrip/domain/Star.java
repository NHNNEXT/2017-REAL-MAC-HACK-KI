package com.amigotrip.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Star {
    @EmbeddedId
    private StarId id;

    public Star(long fromId, long toId) {
        id = new StarId(fromId, toId);
    }
}

@Embeddable
@Data
@NoArgsConstructor
class StarId implements Serializable{
    @NotEmpty
    @Column(name = "from_id")
    @Getter
    private long fromId;

    @NotEmpty
    @Column(name = "to_id")
    private long toId;

    public StarId(long fromId, long toId) {
        this.fromId = fromId;
        this.toId = toId;
    }
}