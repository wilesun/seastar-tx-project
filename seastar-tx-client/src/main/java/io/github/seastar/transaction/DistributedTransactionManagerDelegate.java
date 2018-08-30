package io.github.seastar.transaction;

import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * Distributed Transaction Manager Delegate
 */
public interface DistributedTransactionManagerDelegate {

    void suspendTrace();

    void resumeTrace();

    /**
     * 分布式事务使用异步提交事务,要先做请求事务提交.
     *
     * @param status spring defStatus
     */
    void begCommitDelegate(DefaultTransactionStatus status);

    void triggerCommitDelegate(TransactionStatusSnapshot statusSnapshot);

    void begRollbackDelegate(DefaultTransactionStatus status);

    void triggerRollbackDelegate(TransactionStatusSnapshot statusSnapshot);


}
