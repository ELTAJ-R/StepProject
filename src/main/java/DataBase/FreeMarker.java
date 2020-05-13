package DataBase;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FreeMarker {
    public Configuration config;

    public FreeMarker(String pathName) throws IOException {
        config = new Configuration(Configuration.VERSION_2_3_28) {{
            setDirectoryForTemplateLoading(new File(pathName));
            setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setLogTemplateExceptions(false);
            setWrapUncheckedExceptions(true);
        }}; }}

