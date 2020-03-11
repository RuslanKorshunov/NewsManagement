package com.epam.lab.newsmanagement.config;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.lab.newsmanagement")
public class DaoTestConfig {

    @Bean
    public Logger getLogger() {
        return LogManager.getLogger();
    }

    @Bean
    public DataSource getDataSource() {
        EmbeddedPostgres ep = null;
        try {
            ep = EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            getLogger().error(e);
        }
        return ep.getPostgresDatabase();
    }

    @Bean
    public DatabaseInitializer getDatabaseConfig(DataSource dataSource, Logger logger, ClassLoader classLoader) {
        DatabaseInitializer di = new DatabaseInitializer(dataSource, logger, classLoader);
        di.createDatabase();
        return di;
    }

    @Bean
    public ClassLoader getClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
