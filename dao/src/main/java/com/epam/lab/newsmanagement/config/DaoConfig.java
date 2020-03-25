package com.epam.lab.newsmanagement.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.epam.lab.newsmanagement.dao",
        entityManagerFactoryRef = "getEntityManagerFactory")
@ComponentScan(basePackages = {"com.epam.lab.newsmanagement.dao",
        "com.epam.lab.newsmanagement.validator",
        "com.epam.lab.newsmanagement.entity"})
@PropertySource("classpath:persistence.properties")
public class DaoConfig {
    private static final String URL;
    private static final String USER;
    private static final String DRIVER;
    private static final String PASSWORD;
    private static final String HDM2DDL_AUTO;
    private static final String SHOW_SQL;
    private static final String DIALECT;
    private static final String ENTITY_PACKAGE;

    static {
        URL = "javax.persistence.jdbc.url";
        USER = "javax.persistence.jdbc.user";
        DRIVER = "javax.persistence.jdbc.driver";
        PASSWORD = "javax.persistence.jdbc.password";
        HDM2DDL_AUTO = "hibernate.hbm2ddl.auto";
        SHOW_SQL = "hibernate.show_sql";
        DIALECT = "hibernate.dialect";
        ENTITY_PACKAGE = "com.epam.lab.newsmanagement.entity";
    }

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(environment.getProperty(URL));
        hikariDataSource.setUsername(environment.getProperty(USER));
        hikariDataSource.setPassword(environment.getProperty(PASSWORD));
        hikariDataSource.setDriverClassName(environment.getProperty(DRIVER));
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(ENTITY_PACKAGE);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(getProperties());
        return factoryBean;
    }

    @Bean
    public EntityManager getEntityManager(LocalContainerEntityManagerFactoryBean factoryBean) {
        EntityManager entityManager = factoryBean.getNativeEntityManagerFactory().createEntityManager();
        entityManager.setFlushMode(FlushModeType.COMMIT);
        return entityManager;
    }

    @Bean
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(HDM2DDL_AUTO, environment.getProperty(HDM2DDL_AUTO));
        properties.put(SHOW_SQL, environment.getProperty(SHOW_SQL));
        properties.put(DIALECT, environment.getProperty(DIALECT));
        return properties;
    }
}
