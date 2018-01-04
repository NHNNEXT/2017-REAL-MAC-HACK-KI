package com.amigotrip.domain;

import com.amigotrip.domain.enums.InterestName;
import com.amigotrip.domain.enums.ThemeName;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Woohyeon on 2018. 1. 3..
 */
@Entity
@Data
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theme_id")
    private  long id;

    @Column(name = "theme_name")
    private ThemeName themeName;
}
