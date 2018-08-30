package com.kqgeo.seastar.transaction;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TransactionProcessResponse {

    private String traceId;
    private Integer stackId;
    private Integer spanId;
    private Integer lastSpanId;
    private SpanDefinition.Status status;
    private String description;

    private List<TransactionProcessResponse> previousResponses;


    public TransactionProcessResponse(String traceId, Integer stackId, Integer spanId, Integer lastSpanId) {
        this.traceId = traceId;
        this.stackId = stackId;
        this.spanId = spanId;
        this.lastSpanId = lastSpanId;
    }


    public void initPreviousResponses() {
        previousResponses = new ArrayList<>();
    }


    public void mergeToSpans(List<SpanDefinition> spans) {

        if (CollectionUtils.isEmpty(spans)) {
            return;
        }

        Map<Integer, SpanDefinition> spanMap =
                spans.stream().collect(Collectors.toMap(SpanDefinition::getId, s -> s));


        List<TransactionProcessResponse> processResponses = new LinkedList<>();
        processResponses.add(this);

        if (!CollectionUtils.isEmpty(this.previousResponses)) {
            processResponses.addAll(this.previousResponses);
        }

        processResponses.forEach(rp -> {
            SpanDefinition sp = spanMap.get(rp.getSpanId());
            if (sp != null) {
                sp.setStatus(rp.status);
            }
        });

    }
}
