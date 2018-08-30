package com.kqgeo.seastar.transaction;

import lombok.Data;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;

/**
 * 对 Spring 事务中的 {@link TransactionStatus} 进行描述化,
 * 以便于在分布式事务中可以传递事务状态信息
 */
@Data
public class SpanDefinition {

    /**
     * 协调者
     */
    public static final int KIND_COORDINATOR = 1;

    /**
     * 参与者
     */
    public static final int KIND_PARTICIPATOR = 2;

    public static final int NOT_STATUS = -1;
    public static final int BEG_COMMIT_STATUS = 1;
    public static final int COMMIT_SUCCESS_STATUS = 2;
    public static final int COMMIT_FAILED_STATUS = 3;

    public static final int BEG_ROLLBACK_STATUS = 4;
    public static final int ROLLBACK_SUCCESS_STATUS = 5;

    public static final int ROLLBACK_FAILED_STATUS = 6;

    public static final Integer COORDINATOR_SPAN_ID = 1;

    /**
     * Span 类别. 区分 事务协调者和参与者
     */
    public enum Kind {

        /**
         * 事务的发起人就是事务的协调者<br />
         * 协调者, 协调分布式事务总体提交或者回滚
         */
        COORDINATOR(KIND_COORDINATOR),

        /**
         * 参与者
         */
        PARTICIPATOR(KIND_PARTICIPATOR);

        private final int value;

        Kind(int value) {
            this.value = value;
        }


        public int value() {

            return value;
        }
    }

    public enum Status {

        /**
         * 初始化值
         */
        NOT(NOT_STATUS),

        /**
         * 请求提交, 当前本地事务处理完成. 告知事务协调者, 请求事务提交.<br />
         * 如果在整个分布式事务执行链上, 有一个事务出现回滚将以回滚的方式倍处理.
         */
        BEG_COMMIT(BEG_COMMIT_STATUS),

        /**
         * Span 所描述的 TransactionStatus 事务提交成功
         */
        COMMIT_SUCCESS(COMMIT_SUCCESS_STATUS),

        /**
         * Span 所描述的 TransactionStatus 事务提交失败
         */
        COMMIT_FAILED(COMMIT_SUCCESS_STATUS),

        /**
         * 请求回滚事务
         */
        BEG_ROLLBACK(BEG_ROLLBACK_STATUS),

        /**
         * Span 所描述的 TransactionStatus 事务回滚成功
         */
        ROLLBACK_SUCCESS(ROLLBACK_SUCCESS_STATUS),

        /**
         * Span 所描述的 TransactionStatus 事务回滚失败
         */
        ROLLBACK_FAILED(ROLLBACK_FAILED_STATUS);

        private final int value;

        Status(int value) {
            this.value = value;
        }


        public int value() {

            return value;
        }
    }


    /**
     * 所在 trace 的 id
     */
    private String traceId;

    /**
     * id 是自增长的, 每次向 {@link TraceDefinition} 中添加新的 Span 时, 都会在上一个添加 Span 对象的 id 基础性自增,<br />
     * 第一个添加到 Trace 中的 Span.id 为 1, id 为 1 的 Span 也是整个事务中的协调者.
     */
    private Integer id;

    /**
     * 当前Span的状态值
     */
    private Status status = Status.NOT;

    /**
     * 当前 Span 的类型, SpanKind 类型有: 事务协调者(coordinator)、事务参与者(participator).<br />
     */
    private Kind kind;

    /**
     * Spring 中支持嵌套事务, parentId 为 上一层 TransactionStatus 所对应的 Span 对象的 id
     */
    private Integer parentId;

    /**
     * 所在服务中的服务端 URL, 用于异步调用操作
     */
    private String localEndpoint;

    /**
     * 当前并没有被实现
     */
    private Propagation propagation;

    /**
     * Span 被实例化所在的 ThreadId, 严格意义上来说是和 {@link TransactionStatus} 所在的 ThreadId 为同一个
     */
    private long threadId = Thread.currentThread().getId();

}
