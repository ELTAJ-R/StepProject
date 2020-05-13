package JavaWeb;

import DataBase.FreeMarker;
import DataBase.SQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;

public class LikedProfilesServlet extends HttpServlet {
    SQL db = new SQL();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String currentUser = req.getCookies()[0].getValue();

        try {
            LinkedList<String> allLikes = db.findAllLikes(currentUser);
            resp.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            HashMap<String, LinkedList<String>> map = new HashMap<>();
            map.put("allLikes", allLikes);

            try (PrintWriter w = resp.getWriter()) {
                FreeMarker marker = new FreeMarker("Documents/Code");
                marker.config.getTemplate("Like.ftl").process(map, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

