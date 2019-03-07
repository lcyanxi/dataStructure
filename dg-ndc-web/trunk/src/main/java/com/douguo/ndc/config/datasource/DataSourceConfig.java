package com.douguo.ndc.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by lichang on 2018/8/2
 */
@Configuration public class DataSourceConfig {
    @Bean(name = "primaryDataSource") @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary") public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource") @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary") public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dg2010DataSource") @Qualifier("dg2010DataSource") @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dg2010") public DataSource dg2010DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "dg2010JdbcTemplate")
    public JdbcTemplate dg2010JdbcTemplate(@Qualifier("dg2010DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
