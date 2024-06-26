package com.curd.demo.Routes;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class DataSourceConfig {
    @Bean
     DataSource mydataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/camel");
        dataSource.setUsername("root");
        dataSource.setPassword("Aspire@123");
        return dataSource;
    }
}
