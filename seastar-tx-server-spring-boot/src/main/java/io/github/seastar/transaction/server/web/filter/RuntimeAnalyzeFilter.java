package io.github.seastar.transaction.server.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class RuntimeAnalyzeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        long s = System.currentTimeMillis();

        chain.doFilter(request, response);
        long e = System.currentTimeMillis();

        String method = request.getMethod();

        System.out.println("url: " + request.getRequestURI() + ", " + method + ", using: " + (e - s) + "ms");
    }

    @Override
    public void destroy() {

    }
}
