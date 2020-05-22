package JavaWeb;

import DataBase.FreeMarker;
import DataBase.SQL;
import Entities.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class MessagesServlet extends HttpServlet {
    SQL db = new SQL();
    static int numberOfMessages = 10;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String currentUser = req.getCookies()[0].getValue();
            String userOnView = req.getPathInfo().substring(1);
            List<Message> messages = db.messageLimiter(numberOfMessages, currentUser, userOnView);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("curr", db.getUserByName(currentUser));
            hashMap.put("user", db.getUserByName(userOnView));
            hashMap.put("messages", messages);

            try (PrintWriter w = resp.getWriter()) {
                FreeMarker marker = new FreeMarker(db.htmlLocation, resp);
                marker.config.getTemplate("Messaging.ftl").process(hashMap, w);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentUser = req.getCookies()[0].getValue();
        String userOnView = req.getPathInfo().substring(1);
        String new_message = req.getParameter("message");
        String redirection = String.format("%s/%s", "/messages", userOnView);
        try {
            db.addMessage(currentUser, userOnView, new_message);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        resp.sendRedirect(redirection);


    }
}

