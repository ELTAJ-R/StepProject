package org.eltaj.step.DataBase;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FreeMarker {

    public Configuration config;

//Free marker requires pahtname where .ftl file is situated.
    public FreeMarker(String pathName) throws IOException {
        config = new Configuration(Configuration.VERSION_2_3_28) {{
            setDirectoryForTemplateLoading(new File(pathName));
            setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setLogTemplateExceptions(false);
            setWrapUncheckedExceptions(true);
        }};
    }

// getTemplate requires ftl file's name and map in which our data is stored.
    public void getTemplate(HttpServletResponse response, String fileName, HashMap map, PrintWriter writer) throws IOException, TemplateException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        config.getTemplate(fileName).process(map,writer);
    }
}

