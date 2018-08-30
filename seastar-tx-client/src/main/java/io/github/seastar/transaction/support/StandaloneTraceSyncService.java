package io.github.seastar.transaction.support;

import io.github.seastar.transaction.TraceDefinition;
import io.github.seastar.transaction.TraceSyncService;
import io.github.seastar.transaction.autoconfigure.DistributedTransactionClientProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * 单机版 Tx-Server TraceSyncService 接口 实现
 */
public class StandaloneTraceSyncService implements TraceSyncService, InitializingBean {


    private RestTemplate restTemplate;

    @Autowired
    private DistributedTransactionClientProperties clientProperties;


    @Override
    public void afterPropertiesSet() throws Exception {


        initRestTemplate();

    }

    private void initRestTemplate() {
        restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory());
    }


    @Override
    public TraceDefinition getTrace(String traceId) {


        return restTemplate.getForObject(clientProperties.getUrl() + "/trace/{traceId}", TraceDefinition.class, traceId);
    }

    @Override
    public TraceDefinition syncTrace(TraceDefinition trace) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<TraceDefinition> traceHttpEntity = new HttpEntity<>(trace, headers);

        return restTemplate.postForObject(clientProperties.getUrl() + "/trace", traceHttpEntity, TraceDefinition.class);
    }


    @Override
    public void asyncTrace(TraceDefinition trace) {

    }
}
