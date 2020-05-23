package org.eltaj.step.Filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    //This filter checks if user is already logged in.If yes,he is redirected to Users page until he logs out.
    //Thanks to this filter there is no way to have 2 users in the system at the same time.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain Chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        try {
            Cookie[] cookies = req.getCookies();
            if (cookies.length == 0) Chain.doFilter(req, resp);
            else resp.sendRedirect("/users/*");
        } catch (NullPointerException e) {
            Chain.doFilter(req, resp);}}

    @Override
    public void destroy() {}
}
