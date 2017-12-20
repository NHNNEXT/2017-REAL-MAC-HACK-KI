package com.amigotrip.service;

import com.amigotrip.domain.LocalsArticle;
import com.amigotrip.repository.LocalsArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Slf4j
@Service("service.articleService")
@ComponentScan({"com.amigotrip.domain", "com.amigotrip.web.config"})
public class ArticleService {
    @Resource
    private LocalsArticleRepository localsArticleRepository;

    public List<LocalsArticle> search(String city) {
        return localsArticleRepository.findByLocationLike("%" + city + "%");
    }
}