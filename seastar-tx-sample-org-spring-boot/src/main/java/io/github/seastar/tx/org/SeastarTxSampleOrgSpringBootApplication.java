package io.github.seastar.tx.org;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackageClasses = SeastarTxSampleOrgSpringBootApplication.class)
@SpringBootApplication
public class SeastarTxSampleOrgSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeastarTxSampleOrgSpringBootApplication.class, args);
    }
}
