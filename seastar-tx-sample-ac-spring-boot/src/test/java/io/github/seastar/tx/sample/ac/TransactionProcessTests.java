package io.github.seastar.tx.sample.ac;

import io.github.seastar.transaction.TransactionProcessRequest;
import io.github.seastar.transaction.TransactionProcessResponse;
import io.github.seastar.transaction.support.TransactionProcessUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TransactionProcessTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCommit() throws Exception {

        String traceId = "67f9ba2e-172c-4397-9e17-deef352f7862";
        Integer stackId = 1;
        Integer spanId = 4;

        TransactionProcessRequest request = new TransactionProcessRequest(traceId, stackId, spanId, "http://192.168.0.189:9831/tx-app1");

        TransactionProcessResponse response = TransactionProcessUtils.httpCommit(request);

        System.out.println(response);

    }
}
