package org.eltaj.step.DataBase;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Log4j2
public class FreeMarker {

    public Configuration config;

//Free marker requires pahtname where .ftl file is situated.
    public FreeMarker(String pathName) {
        try {config = new Configuration(Configuration.VERSION_2_3_28) {{
                setDirectoryForTemplateLoading(new File(pathName));
                setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
                setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                setLogTemplateExceptions(false);
                setWrapUncheckedExceptions(true);}};
        }catch(IOException e){log.error("IO exception was thrown while initializing FreeMarker"+e);}
    }

// getTemplate requires ftl file's name and map in which our data is stored.
    public void getTemplate(HttpServletResponse response, String fileName, HashMap map, PrintWriter writer){
       try {response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
           config.getTemplate(fileName).process(map,writer);}
       catch(IOException e){log.error("IO exception was thrown while writing template"+e);}
       catch(TemplateException e){log.error("Template exception was thrown while writing template"+e);}
    }
}

