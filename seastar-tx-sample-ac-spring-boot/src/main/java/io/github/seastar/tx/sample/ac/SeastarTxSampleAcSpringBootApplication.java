package io.github.seastar.tx.sample.ac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackageClasses = SeastarTxSampleAcSpringBootApplication.class)
@SpringBootApplication
public class SeastarTxSampleAcSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeastarTxSampleAcSpringBootApplication.class, args);

    }
}
