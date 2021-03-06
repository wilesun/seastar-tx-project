package io.github.seastar.transaction.support;

import io.github.seastar.transaction.SpanDefinition;
import io.github.seastar.transaction.TraceContext;
import io.github.seastar.transaction.TraceDefinition;
import io.github.seastar.transaction.TraceResourceManager;
import io.github.seastar.transaction.TransactionProcessRequest;
import io.github.seastar.transaction.TransactionProcessResponse;
import io.github.seastar.transaction.TransactionProcessService;
import io.github.seastar.transaction.TransactionStatusSnapshot;
import io.github.seastar.transaction.util.SpanUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * InvokedStack 按照事务创建的顺序来处理事务, 事务处理(commit/rollback).<br />
 * 如果事务创建的顺序为: a -> b -> c -> d, 那么事务处理的顺序为: d -> c -> b -> a.
 */
public class InvokedStackTransactionProcessService implements TransactionProcessService {


//    @FunctionalInterface
//    public static interface TransactionGetPopResponseFunction {
//        TransactionProcessResponse get(TransactionProcessRequest request);
//    }


//    private TransactionProcessResponse getResponse(Integer stackId, Integer spanId, TransactionGetPopResponseFunction popResponseFunction) {
//
//        TraceContext traceContext = TraceContext.getContext();
//        TraceDefinition currentTrace = traceContext.getTrace();
//        String traceId = currentTrace.getTraceId();
//        SpanDefinition span = SpanUtils.getSpan(currentTrace, spanId);
//
//        TransactionProcessResponse popResponse = null;
//        Integer notPopSpanId = -128;
//
//        //
//        if (stackId != null && !stackId.equals(span.getParentId())) {
//            SpanDefinition parentSpan = SpanUtils.getParentSpan(currentTrace, span);
//            if (parentSpan != null) {
//
//                TransactionProcessRequest popRequest =
//                        new TransactionProcessRequest(traceId, stackId, parentSpan.getId(),
//                                SpanUtils.hasLocalSpan(parentSpan), // 是否为本地处理
//                                parentSpan.getLocalEndpoint());
//                popResponse = popResponseFunction.get(popRequest);
//            }
//        }
//
//        if (popResponse == null) {
//            popResponse = new TransactionProcessResponse();
//            popResponse.setSpanId(notPopSpanId);
//            popResponse.setLastSpanId(spanId);
//        }
//
//        // currentTrace 如果 suspendedTraceId 不为空,被 resume 后 currentTrace 和现在的
//        // traceContext 中引用的 trace 不是同一个.
//        // 当前的 trace 等于 traceContext.trace 没有被 resume, 需要从当前线程中清除掉当前的 trace
//        String suspendedTraceId = currentTrace.getSuspendedTraceId();
//        TraceDefinition contextTrace = traceContext.getTrace();
//        if (currentTrace == contextTrace ||
//                suspendedTraceId == null ||
//                !suspendedTraceId.equals(contextTrace.getTraceId())) {
//            traceContext.cleanTrace();
//        }
//
//
//        TransactionProcessResponse response =
//                new TransactionProcessResponse(traceId, stackId, spanId, popResponse.getLastSpanId());
//
//        // 添加上一步操作
//        if (notPopSpanId != popResponse.getSpanId()) {
//            if (response.getPreviousResponses() == null) {
//                response.initPreviousResponses();
//            }
//
//            response.getPreviousResponses().add(popResponse);
//        }
//        if (!CollectionUtils.isEmpty(popResponse.getPreviousResponses())) {
//            if (response.getPreviousResponses() == null) {
//                response.initPreviousResponses();
//            }
//
//            response.getPreviousResponses().addAll(popResponse.getPreviousResponses());
//            popResponse.setPreviousResponses(null);
//        }
//
//        return response;
//
//    }


    @Override
    public TransactionProcessResponse commit(TransactionProcessRequest request) {
        // 是否为本地提交
        if (SpanUtils.hasLocalSpan(request.getEndpoint())) {
            String traceId = request.getTraceId();
            Integer stackId = request.getStackId();
            int spanId = request.getSpanId();

            TraceDefinition trace = TraceContext.getTrace(traceId);
            Assert.notNull(trace, "Trace[ " + traceId + " ] 为空");
            SpanDefinition span = SpanUtils.getSpan(trace, spanId);
            Assert.notNull(span, "Span[ " + spanId + " ] 为空");

            TransactionStatusSnapshot statusSnapshot = TraceResourceManager.getSnapshot(traceId, spanId);

            // 提交状态
            SpanDefinition.Status status;
            try {
                // 委托提交
                statusSnapshot.getTransactionManagerDelegate().triggerCommitDelegate(statusSnapshot);
                TraceResourceManager.removeSnapshot(traceId, spanId);
                status = SpanDefinition.Status.COMMIT_SUCCESS;
            } catch (Exception e) {
                status = SpanDefinition.Status.COMMIT_FAILED;
                e.printStackTrace();
            }

            TransactionProcessResponse popResponse = null;
            Integer notPopSpanId = -128;

            // stackId 与 span.parentId 如果不相同说明 parentId 不是由 stackId
            // 所在的事务处理请求所发起的, 将继续调用 parentId 所在的事务处理服务
            if (stackId != null && !stackId.equals(span.getParentId())) {
                SpanDefinition parentSpan = SpanUtils.getParentSpan(trace, span);
                if (parentSpan != null) {

                    TransactionProcessRequest popRequest =
                            new TransactionProcessRequest(traceId, stackId, parentSpan.getId(),
                                    // 是否为本地处理
                                    parentSpan.getLocalEndpoint());
                    if (SpanUtils.hasLocalSpan(parentSpan.getLocalEndpoint())) {
                        // ParentSpan.endpoint 本地模式, 使用本地提交
                        popResponse = commit(popRequest);
                    } else {
                        popResponse = TransactionProcessUtils.httpCommit(popRequest);
                    }
                }
            }

            if (popResponse == null) {
                popResponse = new TransactionProcessResponse();
                popResponse.setSpanId(notPopSpanId);
                popResponse.setLastSpanId(spanId);
            }

            TransactionProcessResponse response =
                    new TransactionProcessResponse(traceId, stackId, spanId, popResponse.getLastSpanId());

            response.setStatus(status);

            // 合并上一步处理的 Response 信息到最新的 response 对象中
            if (notPopSpanId.equals(popResponse.getSpanId())) {
                if (response.getPreviousResponses() == null) {
                    response.initPreviousResponses();
                }

                response.getPreviousResponses().add(popResponse);
            }
            if (!CollectionUtils.isEmpty(popResponse.getPreviousResponses())) {
                if (response.getPreviousResponses() == null) {
                    response.initPreviousResponses();
                }

                response.getPreviousResponses().addAll(popResponse.getPreviousResponses());
                popResponse.setPreviousResponses(null);
            }

            return response;
        } else {
            return TransactionProcessUtils.httpCommit(request);
        }
    }

    @Override
    public TransactionProcessResponse rollback(TransactionProcessRequest request) {

        throw new RuntimeException("未实现");

//        String traceId = request.getTraceId();
//        Integer stackId = request.getStackId();
//        int spanId = request.getSpanId();
//
//
//        TraceContext traceContext = TraceContext.getContext();
//        TraceDefinition currentTrace = TraceContext.getTrace(traceId);
//
//        traceContext.setTrace(currentTrace);
//
//        TransactionStatusSnapshot statusSnapshot = TraceResourceManager.getSnapshot(traceId, spanId);
//
//        SpanDefinition.Status status;
//        try {
//            statusSnapshot.getTransactionManagerDelegate().triggerRollbackDelegate(statusSnapshot);
//            TraceResourceManager.removeSnapshot(traceId, spanId);
//            status = SpanDefinition.Status.ROLLBACK_SUCCESS;
//        } catch (Exception e) {
//            status = SpanDefinition.Status.ROLLBACK_FAILED;
//            e.printStackTrace();
//        }
//        TransactionProcessResponse response = getResponse(stackId, spanId, (popRequest) -> {
//
//            TransactionProcessResponse popResponse;
//            if (popRequest.isLocalProcess()) {
//                // ParentSpan.endpoint 本地模式, 使用本地提交
//                popResponse = rollback(popRequest);
//            } else {
//                popResponse = TransactionProcessUtils.httpRollback(popRequest);
//            }
//            return popResponse;
//        });
//        response.setStatus(status);
//        return response;

    }
}
