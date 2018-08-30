package io.github.seastar.transaction.server.config;

import io.github.seastar.transaction.server.web.filter.RuntimeAnalyzeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {


    @Bean
    public FilterRegistrationBean<RuntimeAnalyzeFilter> runtimeAnalyzeFilter() {
        FilterRegistrationBean<RuntimeAnalyzeFilter>
                filterRegistrationBean = new FilterRegistrationBean<>();

        RuntimeAnalyzeFilter runtimeAnalyzeFilter = new RuntimeAnalyzeFilter();
        filterRegistrationBean.setFilter(runtimeAnalyzeFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
