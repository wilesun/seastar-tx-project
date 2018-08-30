package com.kqgeo.seastar.transaction.util;

import com.kqgeo.seastar.transaction.TraceDefinition;

import java.util.ArrayList;
import java.util.UUID;

public abstract class TraceUtils {


    private static String nextTraceId() {
        return UUID.randomUUID().toString();
    }


    public static TraceDefinition newTrace() {
        TraceDefinition trace = new TraceDefinition();
        trace.setTraceId(nextTraceId());
        trace.setSpans(new ArrayList<>());
        return trace;
    }


}
