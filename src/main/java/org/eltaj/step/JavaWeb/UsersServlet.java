package org.eltaj.step.JavaWeb;

import freemarker.template.TemplateException;
import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.Pair;
import org.eltaj.step.Entities.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;


public class UsersServlet extends HttpServlet {
    private final SQL db;
    private final FreeMarker marker;
    private final Methods mixedMethods;


    public UsersServlet(SQL db, FreeMarker marker, Methods mixedMethods) {
        this.db = db;
        this.marker = marker;
        this.mixedMethods = mixedMethods;
    }

    public static Pair<Boolean, User> pair;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String currentUser = mixedMethods.findCurrUser(req);
            pair = db.getNextUser(currentUser);
            HashMap<String, User> map = new HashMap(){{put("user", pair.b);}};
            try (PrintWriter w = resp.getWriter()) {
               marker.getTemplate(resp,"Users.ftl",map,w);
            }
        } catch (TemplateException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Cookie[] cookies = req.getCookies();
            if (cookies.length == 1) {
                String currentUser = mixedMethods.findCurrUser(req);
                boolean didYouLike = req.getParameter("first") != null;
                if (didYouLike) {
                    String like = pair.b.name;
                    db.addLike(currentUser, like);
                }
                if (pair.a) {
                    resp.sendRedirect("/like");
                } else
                    resp.sendRedirect("/users");
            }

        } catch (NullPointerException e) {
            resp.sendRedirect("/login/*");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

