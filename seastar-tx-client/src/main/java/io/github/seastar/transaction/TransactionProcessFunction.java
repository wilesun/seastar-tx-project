package io.github.seastar.transaction;


/**
 * 在目前的代码实现中 commit 和 rollback 事务处理的方式是一样的, 将事务处理的 request 和 response
 */
@FunctionalInterface
public interface TransactionProcessFunction {

    /**
     * @param request
     * @return
     */
    TransactionProcessResponse process(TransactionProcessRequest request);
}
