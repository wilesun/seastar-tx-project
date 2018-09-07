package io.github.seastar.tx.sample.ac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DistributedDataSourceTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AcBeansConfiguration {

    @Autowired
    private DataSource dataSource;


    @Bean
    @Qualifier("transactionManager")
    public DistributedDataSourceTransactionManager distributedDataSourceTransactionManager() {

        DistributedDataSourceTransactionManager transactionManager = new DistributedDataSourceTransactionManager(dataSource);
        return transactionManager;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
