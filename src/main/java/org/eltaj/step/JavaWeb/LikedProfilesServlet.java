package org.eltaj.step.JavaWeb;

import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class LikedProfilesServlet extends HttpServlet {
    private final SQL db;
    private final FreeMarker marker;
    private final Methods mixedMethods;

    public LikedProfilesServlet(SQL db, FreeMarker marker, Methods mixedMethods) {
        this.db = db;
        this.marker = marker;
        this.mixedMethods = mixedMethods;}

        @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String currentUser = mixedMethods.findCurrUser(req);
        try {
            List<User> all = db.getAll(currentUser);
            HashMap<String, List<User>> map = new HashMap(){{put("users", all);}};
            try (PrintWriter w = resp.getWriter()) {
               marker.getTemplate(resp,"Like.ftl",map,w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

