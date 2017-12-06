package com.amigotrip.repository;

import com.amigotrip.domain.TravelerReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by NEXT on 2017. 12. 6..
 */
public interface TravelerReplyRepository extends JpaRepository<TravelerReply, Long> {
    List<TravelerReply> findByArticleId(long id);
}
