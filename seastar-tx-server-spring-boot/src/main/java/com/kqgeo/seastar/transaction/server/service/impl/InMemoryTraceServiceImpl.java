package com.kqgeo.seastar.transaction.server.service.impl;

import com.kqgeo.seastar.transaction.SpanDefinition;
import com.kqgeo.seastar.transaction.TraceDefinition;
import com.kqgeo.seastar.transaction.server.service.TraceService;
import com.kqgeo.seastar.transaction.util.SpanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemoryTraceServiceImpl implements TraceService {

    private ConcurrentMap<String, TraceDefinition> traces = new ConcurrentHashMap<>();

    @Override
    public TraceDefinition findOneById(String traceId) {
        return traces.get(traceId);
    }

    @Override
    public List<TraceDefinition> findAll() {

        return Collections.unmodifiableList(new ArrayList<>(traces.values()));
    }


    private void margeUpdateSpans(TraceDefinition newTrace, TraceDefinition oldTrace) {


        List<SpanDefinition> newSpanList = newTrace.getSpans();

        List<SpanDefinition> oldSpanList = oldTrace.getSpans();

        if (CollectionUtils.isEmpty(newSpanList)) {
            oldTrace.setSpans(null);

        } else if (CollectionUtils.isEmpty(oldSpanList)) {

            newTrace.setSpans(SpanUtils.sortedList(newSpanList));


        }
        // 都不为 null, 做合并操作
        else if (!CollectionUtils.isEmpty(newSpanList) && !CollectionUtils.isEmpty(oldSpanList)) {

            // oldSpanMap & newSpanMap
            Map<Integer, SpanDefinition> oldSpanMap = SpanUtils.listToMap(oldTrace.getSpans());
            Map<Integer, SpanDefinition> newSpanMap = SpanUtils.listToMap(newTrace.getSpans());
            List<SpanDefinition> spans = new ArrayList<>();

            for (Map.Entry<Integer, SpanDefinition> newSpanEntry : newSpanMap.entrySet()) {
                Integer newSpanId = newSpanEntry.getKey();
                if (oldSpanMap.containsKey(newSpanId)) {
                    oldSpanMap.remove(newSpanId);
                    SpanDefinition newSpan = newSpanEntry.getValue();
                    spans.add(newSpan);
                } else {
                    spans.add(newSpanEntry.getValue());
                }
            }

            if (!ObjectUtils.isEmpty(oldSpanMap)) {
                spans.addAll(SpanUtils.mapToList(oldSpanMap));
            }


            oldTrace.setSpans(SpanUtils.sortedList(spans));
        }
    }

    @Override
    public TraceDefinition update(TraceDefinition newTrace) {

        String traceId = newTrace.getTraceId();

        TraceDefinition oldTrace = traces.get(traceId);

        //
        if (ObjectUtils.isEmpty(oldTrace)) {
            traces.put(traceId, newTrace);
            return newTrace;
        }
        oldTrace.setSuspendedTraceId(newTrace.getSuspendedTraceId());
        oldTrace.setLastSpanId(newTrace.getLastSpanId());

        margeUpdateSpans(newTrace, oldTrace);

        return oldTrace;
    }

    @Override
    public TraceDefinition delete(String traceId) {
        return traces.remove(traceId);
    }

    @Override
    public int clear() {
        int size = traces.size();
        traces.clear();
        return size;
    }
}
