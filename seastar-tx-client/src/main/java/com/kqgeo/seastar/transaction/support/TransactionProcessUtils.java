package com.kqgeo.seastar.transaction.support;

import com.kqgeo.seastar.transaction.TransactionProcessRequest;
import com.kqgeo.seastar.transaction.TransactionProcessResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TransactionProcessUtils {


    private static final RestTemplate httpClient = new RestTemplate();

    private static final String TRANSACTION_COMMIT_PATH = "/transaction/commit";
    private static final String TRANSACTION_ROLLBACK_PATH = "/transaction/rollback";


    public static TransactionProcessResponse httpCommit(TransactionProcessRequest request) {
        return execute(request.getEndpoint(), TRANSACTION_COMMIT_PATH, request);
    }

    public static TransactionProcessResponse httpRollback(TransactionProcessRequest request) {
        return execute(request.getEndpoint(), TRANSACTION_ROLLBACK_PATH, request);
    }

    private static TransactionProcessResponse execute(String endpoint, String path, TransactionProcessRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<TransactionProcessRequest> requestHttpEntity = new HttpEntity<>(request, headers);
        return httpClient.postForObject(endpoint + path, request, TransactionProcessResponse.class);
    }
}
