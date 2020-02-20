package com.epam.lab.newsmanagement.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.lab.newsmanagement")
@PropertySource("classpath:datasource.properties")
public class WebConfig implements WebMvcConfigurer {
    private static final String URL;
    private static final String USERNAME;
    private static final String DRIVER;
    private static final String PASSWORD;

    static {
        URL = "database_url";
        USERNAME = "database_username";
        DRIVER = "database_driver";
        PASSWORD = "database_password";
    }

    @Autowired
    private Environment environment;

    @Bean
    public DataSource hikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(environment.getProperty(URL));
        hikariDataSource.setUsername(environment.getProperty(USERNAME));
        hikariDataSource.setPassword(environment.getProperty(PASSWORD));
        hikariDataSource.setDriverClassName(environment.getProperty(DRIVER));
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

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
