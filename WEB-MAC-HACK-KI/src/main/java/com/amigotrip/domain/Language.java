package com.amigotrip.domain;

import com.amigotrip.domain.enums.LanguageLevel;
import com.amigotrip.domain.enums.LanguageName;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by Woohyeon on 2018. 1. 3..
 */
@Entity
@Data
public class Language {
    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(name = "user_id")
    private long userId;

    // INSERT INTO LANGUAGE  (LANGUAGE_ID, LEVEL, NAME) VALUES (1, 'EXPERT', 1)
    private LanguageName name;

    private LanguageLevel level;
}
