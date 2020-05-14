package JavaWeb;

import DataBase.FreeMarker;
import DataBase.SQL;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;


public class MessagesServlet extends HttpServlet {
    SQL db = new SQL();
    static private String currentUser;
    static private String userOnView;


    public MessagesServlet() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            currentUser = req.getCookies()[0].getValue();
            userOnView = req.getPathInfo().substring(1);
            String allMessages = db.getAllMessages(currentUser, userOnView);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user", userOnView);
            hashMap.put("messages", allMessages);

            try (PrintWriter w = resp.getWriter()) {
                FreeMarker marker = new FreeMarker("Documents/Code", resp);
                marker.config.getTemplate("Messaging.ftl").process(hashMap, w);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try (PrintWriter w = resp.getWriter()) {
            String new_message = req.getParameter("message");

            String allMessages = db.getAllMessages(currentUser, userOnView);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user", userOnView);
            hashMap.put("messages", allMessages);
            db.addMessage(currentUser, userOnView, new_message);
            FreeMarker marker = new FreeMarker("Documents/Code", resp);
            marker.config.getTemplate("Messaging.ftl").process(hashMap, w);
        } catch (SQLException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
