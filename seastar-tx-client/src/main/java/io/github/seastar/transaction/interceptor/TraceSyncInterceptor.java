package io.github.seastar.transaction.interceptor;

import io.github.seastar.transaction.SpanDefinition;
import io.github.seastar.transaction.TraceContext;
import io.github.seastar.transaction.TraceDefinition;
import io.github.seastar.transaction.TraceSyncService;
import io.github.seastar.transaction.TransactionStatusSnapshot;
import io.github.seastar.transaction.TransactionStatusSnapshotInterceptor;
import io.github.seastar.transaction.TransactionStatusSnapshotInterceptorUtils;
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
