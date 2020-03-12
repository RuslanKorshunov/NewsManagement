package com.epam.lab.newsmanagement.config;

import com.epam.lab.newsmanagement.initializer.DatabaseInitializer;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.epam.lab.newsmanagement.dao",
        "com.epam.lab.newsmanagement.validator"})
public class DaoTestConfig {

    private static final Logger logger = LogManager.getLogger();

    @Bean
    public DataSource getDataSource() {
        EmbeddedPostgres ep = null;
        try {
            ep = EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            logger.error(e);
        }
        return ep.getPostgresDatabase();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean(initMethod = "createDatabase", destroyMethod = "dropDatabase")
    public DatabaseInitializer getDatabaseConfig(DataSource dataSource, ClassLoader classLoader) {
        return new DatabaseInitializer(dataSource, classLoader);
    }

    @Bean
    public ClassLoader getClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }
}
