package org.eltaj.step.JavaWeb;

import lombok.SneakyThrows;
import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.Entities.Message;
import org.eltaj.step.Entities.Pair;
import org.eltaj.step.Entities.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;


public class MessagesServlet extends HttpServlet {
    private final SQL db;
    private final FreeMarker marker;
    private final Methods mixedMethods;

    public MessagesServlet(SQL db, FreeMarker marker, Methods mixedMethods) {
        this.db = db;
        this.marker = marker;
        this.mixedMethods = mixedMethods;
    }

    static int numberOfMessages = 8;

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String currentUser = mixedMethods.findCurrUser(req);
        String userOnView = req.getPathInfo().substring(1);
        List<Message> messages = db.messageLimiter(numberOfMessages, currentUser, userOnView);
        User curr = db.getUserByName(currentUser).get();
        Pair<User, Boolean> isValidUser = mixedMethods.isValidUser(db.getUserByName(userOnView));
       if(isValidUser.b){
           HashMap<String, Object> hashMap =
                new HashMap() {{
                    put("curr", curr);
                    put("user", isValidUser.a);
                    put("messages", messages);
                }};

        try (PrintWriter w = resp.getWriter()) {
            marker.getTemplate(resp, "Messaging.ftl", hashMap, w);
        }
    }
       else resp.sendRedirect("/like/*");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentUser = mixedMethods.findCurrUser(req);
        String userOnView = req.getPathInfo().substring(1);
        String new_message = req.getParameter("message");

        //no way to send an empty message
        boolean shouldAdd = mixedMethods.containsRealValue(new_message);
        String redirection = String.format("%s/%s", "/messages", userOnView);
        if (shouldAdd) db.addMessage(currentUser, userOnView, new_message);
        resp.sendRedirect(redirection);


    }
}

