package org.eltaj.step.JavaWeb;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReferenceServlet extends HttpServlet {
    public static String subPath;

    public ReferenceServlet(String path) {
        this.subPath = path;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String filename = req.getPathInfo();
        String osFileLocation = "src/main/resources/StaticContent";
        Path path = Paths.get(osFileLocation, subPath, filename);
        try (OutputStream os = resp.getOutputStream()) {
            Files.copy(path, os);
        }
    }
}
