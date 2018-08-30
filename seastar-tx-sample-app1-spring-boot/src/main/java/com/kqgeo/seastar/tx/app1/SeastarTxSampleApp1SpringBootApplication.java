package com.kqgeo.seastar.tx.app1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableDiscoveryClient

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackageClasses = SeastarTxSampleApp1SpringBootApplication.class)
@SpringBootApplication
public class SeastarTxSampleApp1SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeastarTxSampleApp1SpringBootApplication.class, args);
    }
}
