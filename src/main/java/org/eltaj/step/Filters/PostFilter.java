package org.eltaj.step.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// determines whether request is POST request if yes, directs this req to doPostFilter method.

public interface PostFilter extends Filter {

   default public void init(FilterConfig filterConfig) throws ServletException {}

   public void doPostFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException;

   default public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if(req.getMethod().equalsIgnoreCase("POST")) doPostFilter(req,resp,chain);
        else chain.doFilter(req,resp);}

   default public void destroy() {}
}
