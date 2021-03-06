package io.github.seastar.transaction;

import io.github.seastar.transaction.util.TraceUtils;
import org.junit.Test;

public class TraceContextTests {


    @Test
    public void testCreateTrace() {

        TraceContext traceContext = TraceContext.getContext();

        if (!traceContext.hasTrace()) {

            traceContext.setTrace(TraceUtils.newTrace());

        }

        TraceDefinition trace = traceContext.getTrace();

    }
}
