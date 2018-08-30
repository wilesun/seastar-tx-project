package io.github.seastar.transaction;


/**
 * Trace 对象同步服务, 将 Trace 对象同步到 Tx-Server 中, 为微服务之间事务传递提供当前操作中的最新的 Trace
 */
public interface TraceSyncService {

    /**
     * 根据 traceId 获得 Tx-Server 中的 Trace 对象
     *
     * @param traceId traceId
     * @return Trace
     */
    TraceDefinition getTrace(String traceId);

    /**
     * 将 trace 对象同步到 Tx-Server 中, 并返回一个新的 Trace 对象, 新的 Trace 对象<br />
     * 是对发送给 Tx-Server 的 Trace 对象 与 Trace-Server 中旧的 Trace 对象 合并后的最新的 Trace 对象.
     *
     * @param trace TraceObject
     * @return newTraceObject
     */
    TraceDefinition syncTrace(TraceDefinition trace);

    /**
     * 异步更新
     *
     * @param trace TraceObject
     */
    void asyncTrace(TraceDefinition trace);

}
