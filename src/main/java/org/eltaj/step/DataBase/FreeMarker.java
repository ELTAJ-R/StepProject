package org.eltaj.step.DataBase;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FreeMarker {
    public Configuration config;
//Free marker requires pahtname where .ftl file is situated("org.eltaj.step.JavaWeb.Documents/Code") and Http response
    public FreeMarker(String pathName, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        config = new Configuration(Configuration.VERSION_2_3_28) {{
            setDirectoryForTemplateLoading(new File(pathName));
            setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setLogTemplateExceptions(false);
            setWrapUncheckedExceptions(true);
        }};
    }
}

