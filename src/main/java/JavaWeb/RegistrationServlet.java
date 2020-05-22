package JavaWeb;

import DataBase.Methods;
import DataBase.SQL;
import Entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.stream.Collectors;


public class RegistrationServlet extends HttpServlet {
    SQL db = new SQL();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String html = new BufferedReader(new FileReader(new File("src/main/java/Documents/HTML/Register.html"))).lines()
                .collect(Collectors.joining("\n"));
        try (PrintWriter w = resp.getWriter()) {
            w.write(html);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User(req.getParameter("name"),
                req.getParameter("surname"),
                req.getParameter("email"),
                req.getParameter("photo"),
                req.getParameter("password"));

        Methods methods = new Methods();
        //no way to register without filling the whole form
        boolean canProceed = methods.allIsFilled(req, 5) && db.register(user);
        if (canProceed) resp.sendRedirect("/login/*");
        else resp.sendRedirect("/register/*");
    }
}

