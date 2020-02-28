package com.epam.lab.newsmanagement.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.lab.newsmanagement")
public class ServiceTestConfig {
    @Bean
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
