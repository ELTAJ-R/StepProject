package org.eltaj.step.JavaWeb;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CookiesGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        String res = Arrays.stream(cookies)
                .map(c -> String.format("%s %s ", c.getName(), c.getValue())).collect(Collectors.joining());
        try (PrintWriter w=resp.getWriter()){
            w.write(res);
        }
    }
}
