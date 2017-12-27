package com.amigotrip.web.config;

import com.amigotrip.helpers.SpringSecurityHelper;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * Created by NEXT on 2017. 12. 27..
 */
@Configuration
@ComponentScan("com.amigotrip.web")
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private HandlebarsViewResolver handlebarsViewResolver;

    @Autowired
    private SpringSecurityHelper springSecurityHelper;

    @PostConstruct
    public void registerHelper() {
        handlebarsViewResolver.registerHelper(SpringSecurityHelper.NAME, springSecurityHelper);
    }
}
