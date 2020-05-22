package JavaWeb;

import DataBase.FreeMarker;
import DataBase.Methods;
import DataBase.SQL;
import Entities.Message;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class MessagesServlet extends HttpServlet {
    SQL db = new SQL();
    static int numberOfMessages = 8;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
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
        Methods method = new Methods();
        String currentUser = req.getCookies()[0].getValue();
        String userOnView = req.getPathInfo().substring(1);
        String new_message = req.getParameter("message");
        //no way to send an empty message
        boolean shouldAdd = method.containsRealValue(new_message);
        String redirection = String.format("%s/%s", "/messages", userOnView);
        if(shouldAdd){
        try { db.addMessage(currentUser, userOnView, new_message);}
        catch (SQLException ex) {}}
        resp.sendRedirect(redirection);


    }
}

