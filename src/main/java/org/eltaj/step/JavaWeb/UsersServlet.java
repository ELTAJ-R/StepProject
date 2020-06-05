package org.eltaj.step.JavaWeb;

import lombok.SneakyThrows;
import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.Pair;
import org.eltaj.step.Entities.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String currentUser = mixedMethods.findCurrUser(req);
        pair = db.getNextUser(currentUser).orElse(new Pair<>(false, User.builder().name("No such user").build()));
        HashMap<String, User> map = new HashMap() {{
            put("user", pair.b);
        }};
        try (PrintWriter w = resp.getWriter()) {
            marker.getTemplate(resp, "Users.ftl", map, w);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
            String currentUser = mixedMethods.findCurrUser(req);
            boolean didYouLike = req.getParameter("first") != null;
            if (didYouLike) {
                String like = pair.b.getName();
                db.addLike(currentUser, like);}
            if (pair.a) resp.sendRedirect("/like");
            else resp.sendRedirect("/users"); }
    }


