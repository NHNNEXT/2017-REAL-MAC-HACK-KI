package amigo.com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Naver on 2017. 11. 9..
 */
@Entity
public class Photo {
    @Id
    private long id;

    @Column(name = "writer_id")
    private long writerId;
}
