package org.eltaj.step.JavaWeb;


import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
@Log4j2
public class LogOutServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
            Cookie[] cookies = req.getCookies();
            Arrays.stream(cookies).forEach(c -> {
                c.setMaxAge(0);
                resp.addCookie(c);});
            resp.sendRedirect("/login/*");
    }
}





