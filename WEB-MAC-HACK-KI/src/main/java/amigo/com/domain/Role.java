package amigo.com.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by Naver on 2017. 11. 12..
 */
@ToString
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    @Getter
    @Setter
    private long id;

    @Column(name = "role")
    @Getter
    @Setter
    private String role;
}
