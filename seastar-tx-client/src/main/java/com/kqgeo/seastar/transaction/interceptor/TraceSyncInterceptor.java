package com.kqgeo.seastar.transaction.interceptor;

import com.kqgeo.seastar.transaction.SpanDefinition;
import com.kqgeo.seastar.transaction.TraceContext;
import com.kqgeo.seastar.transaction.TraceDefinition;
import com.kqgeo.seastar.transaction.TraceSyncService;
import com.kqgeo.seastar.transaction.TransactionStatusSnapshot;
import com.kqgeo.seastar.transaction.TransactionStatusSnapshotInterceptor;
import com.kqgeo.seastar.transaction.TransactionStatusSnapshotInterceptorUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

public class TraceSyncInterceptor implements TransactionStatusSnapshotInterceptor, InitializingBean {


    @Autowired
    private TraceSyncService traceSyncService;

    @Override
    public void afterPropertiesSet() throws Exception {
        TransactionStatusSnapshotInterceptorUtils.registerSnapshotInterceptor(this);
    }


    @Override
    public void afterBegProcess(TransactionStatusSnapshot status, SpanDefinition span, TraceDefinition trace) {
        if (!TraceContext.hasReferencesForLocalInvokedStack(span)) {
            TraceDefinition syncTrace = traceSyncService.syncTrace(trace);
            TraceContext.getContext().setTrace(syncTrace);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
