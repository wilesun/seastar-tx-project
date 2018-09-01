package io.github.seastar.transaction.util;

import io.github.seastar.transaction.SpanDefinition;
import io.github.seastar.transaction.TraceDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SpanUtils {

    private static final String UNKNOWN_ENDPOINT = "unknown_host";

    private static String localEndpoint = "";

    public static Map<Integer, SpanDefinition> listToMap(List<SpanDefinition> spans) {

        if (CollectionUtils.isEmpty(spans)) {
            return Collections.emptyMap();
        }
        return spans
                .stream()
                .collect(Collectors.toMap(SpanDefinition::getId, span -> span));

    }


    public static List<SpanDefinition> mapToList(Map<Integer, SpanDefinition> spans) {

        if (CollectionUtils.isEmpty(spans)) {
            return Collections.emptyList();
        }

        return sortedList(spans.values());
    }


    public static List<SpanDefinition> sortedList(Collection<SpanDefinition> spans) {
        List<SpanDefinition> list = new ArrayList<>(spans);
        Collections.sort(list, Comparator.comparing(SpanDefinition::getId));
        return list;
    }

    public static String getLocalEndpoint() {
        return localEndpoint;
    }

    public static void setLocalEndpoint(String localEndpoint) {
        if (StringUtils.isEmpty(SpanUtils.localEndpoint) ||
                UNKNOWN_ENDPOINT.equals(localEndpoint)) {
            SpanUtils.localEndpoint = localEndpoint;
        }
    }


    public static SpanDefinition newSpan(TraceDefinition trace) {
        SpanDefinition newSpan = new SpanDefinition();

        newSpan.setTraceId(trace.getTraceId());

        List<SpanDefinition> spans = trace.getSpans();

        if (spans == null) {
            spans = new LinkedList<>();
            trace.setSpans(spans);
        }

        int newSpanId = spans.size() + 1;
        newSpan.setId(newSpanId);
        // set kind
        newSpan.setKind(newSpanId == 1 ? SpanDefinition.Kind.COORDINATOR : SpanDefinition.Kind.PARTICIPATOR);

        // set localEndpoint
        newSpan.setLocalEndpoint(SpanUtils.getLocalEndpoint());

        spans.add(newSpan);

        return newSpan;
    }


    public static SpanDefinition getSpan(TraceDefinition trace, int spanId) {
        List<SpanDefinition> spans = trace.getSpans();

        if (CollectionUtils.isEmpty(spans)) {
            return null;
        }

        for (SpanDefinition span : spans) {

            if (span.getId() == spanId) {
                return span;
            }
        }
        return null;
    }


    public static SpanDefinition getParentSpan(TraceDefinition trace, SpanDefinition span) {
        if (span == null) {
            return null;
        }
        Integer parentId = span.getParentId();
        if (parentId == null) {
            return null;
        }
        return getSpan(trace, parentId);
    }

    public static SpanDefinition getDeepSpan(TraceDefinition trace) {
        return trace.getSpans().stream().max(Comparator.comparing(SpanDefinition::getId)).get();
    }


    public static boolean hasLocalSpan(String endpoint) {
        return getLocalEndpoint().equals(endpoint);
    }
}
