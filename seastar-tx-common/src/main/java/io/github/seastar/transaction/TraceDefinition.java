package io.github.seastar.transaction;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private Integer lastSpanId;

    /**
     * 如果为嵌套事务, 设置上一层嵌套 Trace 的 id
     */
    private String suspendedTraceId;

    private List<SpanDefinition> spans;
}
