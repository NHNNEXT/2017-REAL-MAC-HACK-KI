package com.amigotrip.repository;

import com.amigotrip.domain.LocalsReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by NEXT on 2017. 12. 6..
 */
public interface LocalsReplyRepository extends JpaRepository<LocalsReply, Long> {
    List<LocalsReply> findByArticleId(long id);
}
