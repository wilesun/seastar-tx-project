package io.github.seastar.tx.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackageClasses = SeastarTxSampleSpringBootApplication.class)
@SpringBootApplication
public class SeastarTxSampleSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeastarTxSampleSpringBootApplication.class, args);

    }
}
