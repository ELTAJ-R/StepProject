package org.eltaj.step.Filters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginFilter implements HttpFilter {
    //This filter checks if user is already logged in.If yes,he is redirected to Users page until he logs out.
    //Thanks to this filter there is no way to have 2 users in the system at the same time.
    @Override
    public void doHttpFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            Cookie[] cookies = req.getCookies();
            if (cookies.length == 0) chain.doFilter(req, resp);
            else resp.sendRedirect("/users/*");
        } catch (NullPointerException e) {
            log.error("Logged in user was not found.Redirecting to login page:  " + e);
            chain.doFilter(req, resp);
        }
    }}
