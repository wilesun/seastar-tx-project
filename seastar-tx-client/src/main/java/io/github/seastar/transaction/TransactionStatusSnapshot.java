package io.github.seastar.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.core.Ordered;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TransactionStatus 快照, 将 TransactionStatus 中的信息保存到 TraceResourceManager 中,<br />
 * 为分布式事务处理提供异步可操作的 TransactionStatus
 */
@Data
public class TransactionStatusSnapshot implements Ordered {


    private int order = -1;

    private String traceId;

    private int spanId;

    @JsonIgnore
    private DistributedTransactionManagerDelegate transactionManagerDelegate;

    @JsonIgnore
    private DefaultTransactionStatus transactionStatus;

    @JsonIgnore
    private Map<Object, Object> resources;

    @JsonIgnore
    private List<TransactionSynchronization> synchronizations;

    private String currentTransactionName;


    private boolean currentTransactionReadOnly;

    private Integer currentTransactionIsolationLevel;

    private boolean actualTransactionActive;


    public TransactionStatusSnapshot(DefaultTransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


    public boolean hasResources() {
        return !ObjectUtils.isEmpty(resources);
    }

    public boolean hasSynchronizations() {
        return !ObjectUtils.isEmpty(synchronizations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionStatusSnapshot)) return false;
        TransactionStatusSnapshot that = (TransactionStatusSnapshot) o;
        return getOrder() == that.getOrder() &&
                Objects.equals(getTransactionStatus(), that.getTransactionStatus());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getOrder(), getTransactionStatus());
    }
}
