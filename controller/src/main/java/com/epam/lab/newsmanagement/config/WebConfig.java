package com.epam.lab.newsmanagement.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@Configuration
@EnableWebMvc
@Import({DaoConfig.class, ServiceConfig.class})
@ComponentScan(basePackages = "com.epam.lab.newsmanagement.controller")
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, SortCriteria>() {
            @Override
            public SortCriteria convert(String source) {
                SortCriteria sc;
                source = source.toUpperCase();
                try {
                    sc = SortCriteria.valueOf(source);
                } catch (IllegalArgumentException e) {
                    sc = null;
                }
                return sc;
            }
        });
    }
}
