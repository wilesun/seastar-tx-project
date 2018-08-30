package com.kqgeo.seastar.transaction;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class TransactionStatusSnapshotInterceptorUtils {

    public static final List<TransactionStatusSnapshotInterceptor> snapshotInterceptors = new ArrayList<>();

    public static void registerSnapshotInterceptor(TransactionStatusSnapshotInterceptor interceptor) {

        snapshotInterceptors.add(interceptor);
    }

    public static List<TransactionStatusSnapshotInterceptor> getSnapshotInterceptors() {
        return new ArrayList<>(snapshotInterceptors);
    }


    public static void invokeAfterBegProcess(TransactionStatusSnapshot status, SpanDefinition span, TraceDefinition trace) {
        List<TransactionStatusSnapshotInterceptor> interceptors = getSnapshotInterceptors();

        if (!ObjectUtils.isEmpty(interceptors)) {

            for (TransactionStatusSnapshotInterceptor interceptor : interceptors) {
                interceptor.afterBegProcess(status, span, trace);
            }
        }
    }
}
