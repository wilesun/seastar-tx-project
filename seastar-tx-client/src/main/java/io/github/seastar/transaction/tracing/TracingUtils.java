package io.github.seastar.transaction.tracing;

import io.github.seastar.transaction.util.SpanUtils;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

abstract class TracingUtils {

    public static final String TRACE_ID_NAME = "x-dtx-trace-id";

//    public static final String TRACE_OBJECT_NAME = "x-dtx-trace-object";

    private static String localEndpoint = null;

    //
    public static String getTraceId(HttpServletRequest request) throws IOException {

        return request.getHeader(TRACE_ID_NAME);
    }


    public static void setTraceId(HttpRequest request, String traceId) throws IOException {

        request.getHeaders().add(TRACE_ID_NAME, traceId);
    }


    public static void setLocalEndpoint(HttpServletRequest request) {

        if (localEndpoint != null) {
            return;
        }


        String scheme = request.getScheme();
        String localAddress = request.getLocalAddr();
        if ("0:0:0:0:0:0:0:1".equals(localAddress)) {
            localAddress = "127.0.0.1";
        }

        int localPort = request.getLocalPort();
        String contextPath = request.getContextPath();

        localEndpoint =
                new StringBuilder(scheme)
                        .append("://")
                        .append(localAddress)
                        .append(":")
                        .append(localPort)
                        .append(contextPath).toString();

        SpanUtils.setLocalEndpoint(localEndpoint);
    }

    public static String getLocalEndpoint() {
        return localEndpoint;
    }

}
