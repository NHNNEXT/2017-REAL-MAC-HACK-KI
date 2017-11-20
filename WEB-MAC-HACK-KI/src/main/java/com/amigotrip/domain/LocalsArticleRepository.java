package com.amigotrip.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by NEXT on 2017. 11. 16..
 */
public interface LocalsArticleRepository extends JpaRepository<LocalsArticle, Long> {
    List<LocalsArticle> findByWriterId(String writerId);
}
