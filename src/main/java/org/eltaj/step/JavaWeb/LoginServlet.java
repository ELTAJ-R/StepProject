package org.eltaj.step.JavaWeb;


import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class LoginServlet extends HttpServlet {
    private final SQL db;
    private final Methods mixedMethods;

    public LoginServlet(SQL db, Methods mixedMethods) {
        this.db = db;
        this.mixedMethods = mixedMethods;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String location = String.format("%s/%s", db.htmlLocation, "Login.html");
        String result = mixedMethods.getFileOrMessage(location);
        try (PrintWriter w = resp.getWriter()) {
            w.write(result);
        }}


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        boolean isLoggedIn = db.login(user, password);
        if (isLoggedIn) {
            Cookie cookie = new Cookie("user", user);
            cookie.setPath("/");
            resp.addCookie(cookie);
            db.updateLoginDate(user);
            resp.sendRedirect("/users/*");
        } else resp.sendRedirect("/login");


    }
}
