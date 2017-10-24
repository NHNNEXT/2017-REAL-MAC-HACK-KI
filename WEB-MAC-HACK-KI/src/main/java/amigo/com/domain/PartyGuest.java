package amigo.com.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Naver on 2017. 10. 17..
 */

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartyGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private int age;

    @Setter
    @Getter
    private String gender;

    @Setter
    @Getter
    private String language;

    @Getter
    @Setter
    private String date;

    @Setter
    @Getter
    private String theme;

    @Setter
    @Getter
    private String attraction;
}
