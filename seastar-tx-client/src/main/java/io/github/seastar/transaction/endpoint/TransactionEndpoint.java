package io.github.seastar.transaction.endpoint;

import io.github.seastar.transaction.TransactionProcessRequest;
import io.github.seastar.transaction.TransactionProcessResponse;
import io.github.seastar.transaction.TransactionProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分布式事务 Rest 处理接口
 */
@RestController
@RequestMapping("/transaction")
public class TransactionEndpoint {

    @Autowired
    private TransactionProcessService transactionProcessService;

    /**
     * 异步提交事务请求
     *
     * @param request
     * @return
     */
    @PostMapping("/commit")
    public TransactionProcessResponse commitPost(@RequestBody TransactionProcessRequest request) {
        return transactionProcessService.commit(request);
    }


    /**
     * 异步回滚事务请求
     *
     * @param request
     * @return
     */
    @PostMapping("/rollback")
    public TransactionProcessResponse rollbackPost(@RequestBody TransactionProcessRequest request) {
        return transactionProcessService.rollback(request);
    }


//    @GetMapping("/snapshots")
//    public Object snapshotsGet() {
//        return TraceResourceManager.getSnapshots();
//    }
//
//    @GetMapping("/resources")
//    public Object resourcesGet() {
//
//        ConcurrentMap<String, Map<Object, Object>> map = TraceResourceManager.getResources();
//        if (map != null) {
//            return map.keySet();
//        }
//        return null;
//    }
//
//    @GetMapping("/context")
//    public Object threadIdGet() {
//
//
//        TraceContext context = TraceContext.getContext();
//
//        return context.getTrace();
////        return Thread.currentThread().getId();
//    }
//
//
//    @GetMapping("/resource_map")
//    public Object resourceMapGet() {
//
//        long threadId = Thread.currentThread().getId();
//
//        int size1 = 0;
//        if (TransactionSynchronizationManager.isSynchronizationActive()) {
//            size1 = TransactionSynchronizationManager.getSynchronizations().size();
//        }
//        int size2 = TransactionSynchronizationManager.getResourceMap().size();
//        return Arrays.asList(threadId, size1, size2);
//    }


}
