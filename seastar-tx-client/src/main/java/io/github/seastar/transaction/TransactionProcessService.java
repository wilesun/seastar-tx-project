package io.github.seastar.transaction;


/**
 * 事务处理服务, 提供 {@linkplain #commit(TransactionProcessRequest) commit} 和
 * {@linkplain #rollback(TransactionProcessRequest) rollback} 操作. 将原有的 Spring 中
 * {@linkplain org.springframework.transaction.PlatformTransactionManager PlatformTransactionManager} 的自动管理的事务<br />
 * 操作使用此接口提供的服务来进行手动操作, 以此来提供分布式事务的异步提交或者回滚的操作.
 */
public interface TransactionProcessService {

    /**
     * 事务提交操作, 根据 {@linkplain TransactionProcessRequest} 封装的请求参数来请求事务提交
     *
     * @param request
     * @return
     */
    TransactionProcessResponse commit(TransactionProcessRequest request);

    /**
     * 事务回滚操作
     *
     * @param request
     * @return
     */
    TransactionProcessResponse rollback(TransactionProcessRequest request);


}
