package org.eltaj.step.Filters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CookieFilter implements HttpFilter {
    //Check if user is authorized to enter if not redirect to login page
    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            Cookie[] cookies = req.getCookies();
            if (cookies.length == 0) resp.sendRedirect("/login/*");
            else chain.doFilter(req, resp);
        } catch (NullPointerException e) {
            log.error("Operation was unsuccessful because no logged in user was found:  " + e);
            resp.sendRedirect("/login/*");
        }}}
