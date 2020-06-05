package org.eltaj.step.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// determines whether request is POST request if yes, directs this req to doPostFilter method.

public interface PostFilter extends HttpFilter {

    default void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().equalsIgnoreCase("POST")) doPostFilter(req, resp, chain);
        else chain.doFilter(req, resp);
    }

    void doPostFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException;
}