package amigo.com.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by Naver on 2017. 11. 8..
 */
@Entity
public class Review {
    @Id
    private long id;

    @NotEmpty
    @Column(name = "from_id")
    private long fromId;

    @NotEmpty
    @Column(name = "to_id")
    private long toId;

    @NotEmpty
    private String contents;

    @NotEmpty
    private LocalDateTime createdDate;
}
