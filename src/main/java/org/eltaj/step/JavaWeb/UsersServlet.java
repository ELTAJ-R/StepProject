package org.eltaj.step.JavaWeb;

import freemarker.template.TemplateException;
import org.eltaj.step.DataBase.FreeMarker;
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
    SQL db = new SQL();
    public static Pair<Boolean, User> pair;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Cookie[] cookies = req.getCookies();
            String currentUser = cookies[0].getValue();
            pair = db.getNextUser(currentUser);
            HashMap<String, User> map = new HashMap<>();
            map.put("user", pair.b);
            try (PrintWriter w = resp.getWriter()) {
                FreeMarker freeMarker = new FreeMarker(db.htmlLocation, resp);
                freeMarker.config.getTemplate("Users.ftl").process(map, w);
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
                String currentUser = cookies[0].getValue();
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

