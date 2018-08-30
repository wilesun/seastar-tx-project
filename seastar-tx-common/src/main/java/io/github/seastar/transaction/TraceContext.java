package io.github.seastar.transaction;

import io.github.seastar.transaction.util.SpanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TraceContext
 */
public class TraceContext {

    private static final ThreadLocal<TraceContext> traceContext = new ThreadLocal<>();

    private static final ConcurrentMap<String, TraceDefinition> traces = new ConcurrentHashMap<>();


    private TraceDefinition trace;

    private TraceContext() {
    }


    public static TraceContext getContext() {
        TraceContext traceContextObject = traceContext.get();
        if (traceContextObject == null) {
            traceContextObject = new TraceContext();
            traceContext.set(traceContextObject);
        }
        return traceContextObject;
    }


    public static Map<String, TraceDefinition> getTraces() {
        return Collections.unmodifiableMap(new HashMap<>(traces));
    }

    public static TraceDefinition getTrace(String traceId) {
        if (ObjectUtils.isEmpty(traces)) {
            return null;
        }
        return traces.get(traceId);
    }


    public static TraceDefinition removeTrace(String traceId) {
        return traces.remove(traceId);
    }


    public static void cleanupContext() {
        traceContext.remove();
    }

    public static void cleanupContextForNonThreadReferences(SpanDefinition spanRef) {

        TraceContext currentContext = traceContext.get();
        // 如果 ParentSpan 和 Span 不在同一个 Endpoint 里,
        // 或者在同一个 Endpoint 但是 ThreadId 不相同
        // 清空 TraceContext
        if (currentContext != null && !hasReferencesForLocalInvokedStack(spanRef)) {
            traceContext.remove();
        }
    }

    public static boolean hasReferencesForLocalInvokedStack(SpanDefinition spanRef) {
        TraceDefinition trace = traceContext.get().getTrace();
        if (trace != null) {
            Integer parentSpanId = spanRef.getParentId();

            if (parentSpanId == null) {
                return false;
            }

            SpanDefinition parentSpan = SpanUtils.getSpan(trace, parentSpanId);

            if (parentSpan == null) {
                return false;
            }

            String parentEndpoint = parentSpan.getLocalEndpoint();
            long parentThreadId = parentSpan.getThreadId();
            return parentEndpoint.equals(spanRef.getLocalEndpoint()) && spanRef.getThreadId() == parentThreadId;
        }
        return false;
    }

    public TraceDefinition getTrace() {
        return trace;
    }

    public boolean hasTrace() {
        return trace != null;
    }


    public void setTrace(TraceDefinition trace) {
        traces.put(trace.getTraceId(), trace);
        this.trace = trace;
    }

//    public TraceDefinition suspendTrace(TraceDefinition newTrace) {
//        TraceDefinition trace = getTrace();
//        if (trace != null) {
//            newTrace.setSuspendedTraceId(trace.getTraceId());
//        }
//        return trace;
//    }


    public void cleanTrace() {
        this.trace = null;
    }


}
