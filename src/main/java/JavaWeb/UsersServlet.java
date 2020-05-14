package JavaWeb;

import DataBase.FreeMarker;
import DataBase.SQL;
import freemarker.template.TemplateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;


public class UsersServlet extends HttpServlet {
    SQL db = new SQL();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        String currentUser = cookies[0].getValue();

        try (PrintWriter w = resp.getWriter()) {
            FreeMarker freeMarker = new FreeMarker("Documents/Code",resp);
            freeMarker.config.getTemplate("Users.ftl").process(db.getMap(currentUser), w);
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
                    String like = db.userOnView;
                    db.addLike(currentUser, like);}
                if (db.clickedAll(currentUser)) {
                    resp.sendRedirect("/like"); }

               else resp.sendRedirect("/users");
            }

        } catch (NullPointerException e) {
            resp.sendRedirect("/login/*");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

