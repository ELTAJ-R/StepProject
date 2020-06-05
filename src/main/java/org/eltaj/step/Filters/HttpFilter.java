package org.eltaj.step.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpFilter extends Filter {

    default void init(FilterConfig filterConfig) throws ServletException {

    }

    void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException;

    default void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        doHttpFilter(req, resp, chain);
    }


    default void destroy() {

    }
}
