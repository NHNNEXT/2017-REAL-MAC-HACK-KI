package com.amigotrip.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * Created by NEXT on 2017. 12. 7..
 */
@Slf4j
@Service("service.articleService")
@ComponentScan({"com.amigotrip.domain", "com.amigotrip.web.config"})
public class ArticleService {
}
