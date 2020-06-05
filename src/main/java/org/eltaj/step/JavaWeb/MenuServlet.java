package org.eltaj.step.JavaWeb;

import lombok.SneakyThrows;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class MenuServlet extends HttpServlet {
    private final SQL db;
    private final Methods mixedMethods;

    public MenuServlet(SQL db, Methods mixedMethods) {
        this.db = db;
        this.mixedMethods = mixedMethods;
    }
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String location = String.format("%s/%s", db.htmlLocation, "Menu.html");
        String result = mixedMethods.getFileOrMessage(location);
        try (PrintWriter w= resp.getWriter()){
            w.write(result);
        }

    }
}
