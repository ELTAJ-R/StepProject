package org.eltaj.step.JavaWeb;


import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class RegistrationServlet extends HttpServlet {
    private final SQL db;
    private final Methods mixedMethods;

    public RegistrationServlet(SQL db,  Methods mixedMethods) {
        this.db = db;
        this.mixedMethods = mixedMethods;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String location = String.format("%s/%s", db.htmlLocation, "Register.html");
        String result = mixedMethods.getFileOrMessage(location);
        try (PrintWriter w = resp.getWriter()) {
            w.write(result);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = User.builder()
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .email(req.getParameter("email"))
                .picture(req.getParameter("photo"))
                .password(req.getParameter("password"))
                .build();

        boolean couldRegister = db.register(user);
        if (couldRegister) resp.sendRedirect("/login/*");
        else resp.sendRedirect("/register/*");
    }


}

