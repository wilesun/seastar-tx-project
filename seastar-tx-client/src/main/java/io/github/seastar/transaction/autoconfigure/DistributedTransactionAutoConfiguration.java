package io.github.seastar.transaction.autoconfigure;


import io.github.seastar.transaction.TraceSyncService;
import io.github.seastar.transaction.endpoint.TransactionEndpoint;
import io.github.seastar.transaction.interceptor.TraceSyncInterceptor;
import io.github.seastar.transaction.support.InvokedStackTransactionProcessService;
import io.github.seastar.transaction.support.StandaloneTraceSyncService;
import io.github.seastar.transaction.tracing.DistributedTransactionTracingClientHttpRequestInterceptor;
import io.github.seastar.transaction.tracing.DistributedTransactionTracingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@EnableConfigurationProperties(DistributedTransactionClientProperties.class)
@Configuration
public class DistributedTransactionAutoConfiguration {


//    @Bean
//    public TraceResourceManager traceResourceManager() {
//
//        return new DefaultTraceResourceManager();
//    }


    @Bean
    public DistributedTransactionTracingClientHttpRequestInterceptor distributedTransactionTracingClientHttpRequestInterceptor(@Autowired(required = false) RestTemplate restTemplate) {


        DistributedTransactionTracingClientHttpRequestInterceptor interceptor = new DistributedTransactionTracingClientHttpRequestInterceptor();
        restTemplate.getInterceptors().add(interceptor);
        return interceptor;

    }


    @Bean
    public FilterRegistrationBean<DistributedTransactionTracingFilter> distributedTransactionTracingFilterFilter(TraceSyncService traceSyncService) {
        FilterRegistrationBean<DistributedTransactionTracingFilter>
                filterRegistrationBean = new FilterRegistrationBean<>();

        DistributedTransactionTracingFilter distributedTransactionTracingFilter = new DistributedTransactionTracingFilter();
        distributedTransactionTracingFilter.setTraceSyncService(traceSyncService);
        filterRegistrationBean.setFilter(distributedTransactionTracingFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }


    @Bean
    public TraceSyncInterceptor traceSyncInterceptor() {
        return new TraceSyncInterceptor();
    }


    @Bean
    public StandaloneTraceSyncService standaloneTraceSyncService() {
        return new StandaloneTraceSyncService();
    }


    @Bean
    public TransactionEndpoint transactionEndpoint() {
        return new TransactionEndpoint();
    }

    @Bean
    public InvokedStackTransactionProcessService invokedStackTransactionProcessService() {
        return new InvokedStackTransactionProcessService();
    }
}
