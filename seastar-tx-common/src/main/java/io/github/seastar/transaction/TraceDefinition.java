package io.github.seastar.transaction;

import lombok.Data;

import java.util.List;

/**
 * 分布式事务执行链的描述定义. 在一条分布式事务执行链中只存在一个 Trace 对象
 */
@Data
public class TraceDefinition {


    /**
     * traceId
     */
    private String traceId;

    /**
     * last span id
     */
    private Integer lastSpanId;


    /**
     * trace 更新版本, 在向 Tx-Server 中更新 Trace 数据的时候, 如果数据有变动版本会增加
     */
    private int version = 0;

    /**
     * 如果为嵌套事务, 设置上一层嵌套 Trace 的 id
     */
    private String suspendedTraceId;

    private List<SpanDefinition> spans;
}
