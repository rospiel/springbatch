package com.study.studyapplicationbatch.core;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "application.datasource")
    public DataSourceProperties propertiesApplication() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties properties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "application.datasource")
    public DataSource springDataSourceApplication(@Qualifier("propertiesApplication") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springDataSource(@Qualifier("properties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerSpringDataSourceApplication(
            @Qualifier("springDataSourceApplication") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
