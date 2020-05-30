package org.eltaj.step.JavaWeb;

import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;


public class LoginServlet extends HttpServlet {
    private final SQL db;
    private final FreeMarker marker;
    private final Methods mixedMethods;

    public LoginServlet(SQL db, FreeMarker marker, Methods mixedMethods) {
        this.db = db;
        this.marker = marker;
        this.mixedMethods = mixedMethods;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String location = String.format("%s/%s", db.htmlLocation, "Login.html");
        String result = mixedMethods.getFileOrMessage(location);
        try (PrintWriter w = resp.getWriter()) {
            w.write(result);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isLoggedIn = false;
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        try {
            isLoggedIn = db.login(user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isLoggedIn) {
            Cookie cookie = new Cookie("user", user);
            cookie.setPath("/");
            resp.addCookie(cookie);
            db.updateLoginDate(user);
            resp.sendRedirect("/users/*");
        } else resp.sendRedirect("/login");


    }
}
