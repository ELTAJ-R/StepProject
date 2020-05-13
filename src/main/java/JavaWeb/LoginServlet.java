package JavaWeb;

import DataBase.SQL;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class LoginServlet extends HttpServlet {
    SQL db = new SQL();
    public static boolean isLoggedIn = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = new BufferedReader(new FileReader(new File("Documents/Code/Login.html"))).lines()
                .collect(Collectors.joining("\n"));
        try (PrintWriter w = resp.getWriter()) {
            w.write(result);
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String password = req.getParameter("password");
        try {
            isLoggedIn = db.login(user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isLoggedIn) {
            Cookie cookie = new Cookie("user", user);
            cookie.setPath("/");
            resp.addCookie(cookie);
            resp.sendRedirect("/users/*");
        } else resp.sendRedirect("/login");


    }
}
