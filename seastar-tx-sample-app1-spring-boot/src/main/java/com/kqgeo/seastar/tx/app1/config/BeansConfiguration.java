package com.kqgeo.seastar.tx.app1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DistributedDataSourceTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class BeansConfiguration {


    @Autowired
    private DataSource dataSource;


    @Bean
    @Qualifier("transactionManager")
    public DistributedDataSourceTransactionManager distributedDataSourceTransactionManager() {

        DistributedDataSourceTransactionManager transactionManager = new DistributedDataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
