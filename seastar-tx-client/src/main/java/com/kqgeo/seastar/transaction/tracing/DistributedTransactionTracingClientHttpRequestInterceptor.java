package com.kqgeo.seastar.transaction.tracing;

import com.kqgeo.seastar.transaction.TraceContext;
import com.kqgeo.seastar.transaction.TraceDefinition;
import com.kqgeo.seastar.transaction.TraceSyncService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate 拦截器<br />
 * 在 request 的 header 中添加 trace-id 并将 trace 对象 同步到 tx-server<br />
 * 在 response 响应后, 获得 tx-server 里的 trace 对象 更新当前 traceContext 中的 trace
 */
public class DistributedTransactionTracingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {


    @Setter
    @Autowired
    private TraceSyncService traceSyncService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        TraceContext traceContext = TraceContext.getContext();
        TraceDefinition trace = traceContext.getTrace();

        if (trace != null) {
            // Sync Trace to Tx-Server
            trace = traceSyncService.syncTrace(trace);
            traceContext.setTrace(trace);
            TracingUtils.setTraceId(request, trace.getTraceId());
        }

        ClientHttpResponse response = execution.execute(request, body);

        if (trace != null) {
            // Get newTraceObject from Tx-Server
            trace = traceSyncService.getTrace(trace.getTraceId());
            traceContext.setTrace(trace);
        }


        return response;
    }
}
