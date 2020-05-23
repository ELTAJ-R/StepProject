package org.eltaj.step.JavaWeb;

import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class LikedProfilesServlet extends HttpServlet {
    SQL db = new SQL();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String currentUser = req.getCookies()[0].getValue();

        try {
            List<User> all = db.getAll(currentUser);
            HashMap<String, List<User>> map = new HashMap<>();
            map.put("users", all);

            try (PrintWriter w = resp.getWriter()) {
                FreeMarker marker = new FreeMarker(db.htmlLocation, resp);
                marker.config.getTemplate("Like.ftl").process(map, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

