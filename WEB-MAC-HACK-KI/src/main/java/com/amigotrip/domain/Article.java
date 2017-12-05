package com.amigotrip.domain;

/**
 * Created by NEXT on 2017. 11. 30..
 */
public interface Article {

    void updateArticle(Article article);
    void addReply(Reply reply);
    void deleteReply(Reply reply);
}
