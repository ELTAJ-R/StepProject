package org.eltaj.step.Filters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
public class CookieFilter implements Filter {
    //Check if user is authorized to enter if not redirect to login page


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain Chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) (req);
        HttpServletResponse response = (HttpServletResponse) (resp);
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies.length == 0) response.sendRedirect("/login/*");
            else Chain.doFilter(request, response);
        } catch (NullPointerException e) {
            log.error("Operation was unsuccessful because no logged in user was found:  "+e);
            response.sendRedirect("/login/*");}}

    @Override
    public void destroy() {}
}
