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
import org.springframework.util.CollectionUtils;

public class InvokedStackTransactionProcessService implements TransactionProcessService {


    @FunctionalInterface
    public static interface TransactionGetPopResponseFunction {
        TransactionProcessResponse get(TransactionProcessRequest request);
    }


    private TransactionProcessResponse getResponse(Integer stackId, Integer spanId, TransactionGetPopResponseFunction popResponseFunction) {

        TraceContext traceContext = TraceContext.getContext();

        TraceDefinition currentTrace = traceContext.getTrace();
        String traceId = currentTrace.getTraceId();
        SpanDefinition span = SpanUtils.getSpan(currentTrace, spanId);

        TransactionProcessResponse popResponse = null;
        Integer notPopSpanId = -128;

        //
        if (stackId != null && !stackId.equals(span.getParentId())) {
            SpanDefinition parentSpan = SpanUtils.getParentSpan(currentTrace, span);
            if (parentSpan != null) {

                TransactionProcessRequest popRequest =
                        new TransactionProcessRequest(traceId, stackId, parentSpan.getId(),
                                SpanUtils.hasLocalSpan(parentSpan), // 是否为本地处理
                                parentSpan.getLocalEndpoint());
                popResponse = popResponseFunction.get(popRequest);
            }
        }

        if (popResponse == null) {
            popResponse = new TransactionProcessResponse();
            popResponse.setSpanId(notPopSpanId);
            popResponse.setLastSpanId(spanId);
        }

        // currentTrace 如果 suspendedTraceId 不为空,被 resume 后 currentTrace 和现在的
        // traceContext 中引用的 trace 不是同一个.
        // 当前的 trace 等于 traceContext.trace 没有被 resume, 需要从当前线程中清除掉当前的 trace
        String suspendedTraceId = currentTrace.getSuspendedTraceId();
        TraceDefinition contextTrace = traceContext.getTrace();
        if (currentTrace == contextTrace ||
                suspendedTraceId == null ||
                !suspendedTraceId.equals(contextTrace.getTraceId())) {
            traceContext.cleanTrace();
        }


        TransactionProcessResponse response =
                new TransactionProcessResponse(traceId, stackId, spanId, popResponse.getLastSpanId());

        // 添加上一步操作
        if (notPopSpanId != popResponse.getSpanId()) {
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

    }


    @Override
    public TransactionProcessResponse commit(TransactionProcessRequest request) {

        String traceId = request.getTraceId();
        Integer stackId = request.getStackId();
        int spanId = request.getSpanId();

        TraceContext.getContext().setTrace(TraceContext.getTrace(traceId));


        TransactionStatusSnapshot statusSnapshot = TraceResourceManager.getSnapshot(traceId, spanId);

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


        TransactionProcessResponse response =
                getResponse(stackId, spanId, (popRequest) -> {

                    TransactionProcessResponse popResponse;
                    if (popRequest.isLocalProcess()) {
                        // ParentSpan.endpoint 本地模式, 使用本地提交
                        popResponse = commit(popRequest);
                    } else {
                        popResponse = TransactionProcessUtils.httpCommit(popRequest);
                    }
                    return popResponse;
                });
        response.setStatus(status);

        return response;
    }

    @Override
    public TransactionProcessResponse rollback(TransactionProcessRequest request) {


        String traceId = request.getTraceId();
        Integer stackId = request.getStackId();
        int spanId = request.getSpanId();


        TraceContext traceContext = TraceContext.getContext();
        TraceDefinition currentTrace = TraceContext.getTrace(traceId);

        traceContext.setTrace(currentTrace);

        TransactionStatusSnapshot statusSnapshot = TraceResourceManager.getSnapshot(traceId, spanId);

        SpanDefinition.Status status;
        try {
            statusSnapshot.getTransactionManagerDelegate().triggerRollbackDelegate(statusSnapshot);
            TraceResourceManager.removeSnapshot(traceId, spanId);
            status = SpanDefinition.Status.ROLLBACK_SUCCESS;
        } catch (Exception e) {
            status = SpanDefinition.Status.ROLLBACK_FAILED;
            e.printStackTrace();
        }
        TransactionProcessResponse response = getResponse(stackId, spanId, (popRequest) -> {

            TransactionProcessResponse popResponse;
            if (popRequest.isLocalProcess()) {
                // ParentSpan.endpoint 本地模式, 使用本地提交
                popResponse = rollback(popRequest);
            } else {
                popResponse = TransactionProcessUtils.httpRollback(popRequest);
            }
            return popResponse;
        });
        response.setStatus(status);
        return response;

    }
}
