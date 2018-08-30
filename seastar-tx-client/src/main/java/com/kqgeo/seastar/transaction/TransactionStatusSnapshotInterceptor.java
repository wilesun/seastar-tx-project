package com.kqgeo.seastar.transaction;

import org.springframework.core.Ordered;

/**
 * 事务状态快照拦截器
 */
public interface TransactionStatusSnapshotInterceptor extends Ordered {

    /**
     * 在做请求事务时,进入此拦截器
     *
     * @param status 状态快照
     * @param span   Span
     * @param trace  Trace
     */
    void afterBegProcess(TransactionStatusSnapshot status, SpanDefinition span, TraceDefinition trace);
}
