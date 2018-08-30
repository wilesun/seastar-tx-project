package com.kqgeo.seastar.transaction.tracing;

import com.kqgeo.seastar.transaction.TraceContext;
import com.kqgeo.seastar.transaction.TraceDefinition;
import com.kqgeo.seastar.transaction.TraceSyncService;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet WebFilter 处理存在 {@link TracingUtils#TRACE_ID_NAME} RequestHeader,<br />
 * 获得最新的 traceObject, 并在 chain.doFilter 执行后清空 TraceContext.
 */
public class DistributedTransactionTracingFilter implements Filter {

    @Setter
    private TraceSyncService traceSyncService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) resp;

        String traceId = TracingUtils.getTraceId(request);

        TracingUtils.setLocalEndpoint(request);

        // 获得同步数据
        if (!ObjectUtils.isEmpty(traceId)) {
            TraceDefinition syncTrace = traceSyncService.getTrace(traceId);
            TraceContext.getContext().setTrace(syncTrace);
        }

        chain.doFilter(request, response);

        if (!ObjectUtils.isEmpty(traceId)) {
            TraceContext.cleanupContext();
        }
    }

    @Override
    public void destroy() {

    }
}
