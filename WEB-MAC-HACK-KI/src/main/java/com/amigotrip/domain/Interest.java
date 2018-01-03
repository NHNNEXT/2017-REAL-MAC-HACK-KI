package com.amigotrip.domain;

import com.amigotrip.domain.enums.InterestName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Woohyeon on 2018. 1. 3..
 */
@Entity
@Data
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "interest_id")
    private  long id;

    @Column(name = "interest_name")
    private InterestName interestName;
}
