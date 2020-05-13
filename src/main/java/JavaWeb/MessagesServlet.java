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
import java.util.stream.Collectors;

public class MessagesServlet extends HttpServlet {
    SQL db = new SQL();
    static String URL;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL = null;

        String res = new BufferedReader(new FileReader(new File("Documents/Code/Chat.html"))).lines()
                .collect(Collectors.joining("\n"));
        try (PrintWriter w = resp.getWriter()) {
            w.write(res);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String new_message = req.getParameter("message");
        String currentUser = req.getCookies()[0].getValue();
        String userOnView = req.getPathInfo().substring(1);


        try {
            db.addMessage(currentUser, userOnView, new_message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PrintWriter w = resp.getWriter()) {
            String fromMe = db.showMessagesFromMe(currentUser, userOnView);
            String toMe = db.showMessagesToMe(currentUser, userOnView);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("userToMessage", userOnView);
            hashMap.put("fromMe",fromMe);
            hashMap.put("toMe",toMe);
            FreeMarker marker = new FreeMarker("Documents/Code");
            marker.config.getTemplate("Chat.html").process(hashMap, w);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
