import lombok.extern.log4j.Log4j2;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.DataBase.Updater.InitializeSQL;
import org.eltaj.step.DataBase.HerokuEnv;
import org.eltaj.step.Filters.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eltaj.step.JavaWeb.*;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

@Log4j2
public class WebServerApp {
    public static void main(String[] args) throws Exception {

        log.info("initializing classes in order to pass to servlets");
        HerokuEnv env = new HerokuEnv();
        InitializeSQL updater = new InitializeSQL();
        Methods mixedMethods = new Methods();
        SQL db = new SQL(mixedMethods);
        FreeMarker marker = new FreeMarker(db.htmlLocation);

        log.info(" updating and/or creating database prior to starting server");
        updater.autoUpdate();


        log.info("mapping servlets to URL");
        Server server = new Server(env.port());
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new UsersServlet(db, marker, mixedMethods)), "/users/*");
        handler.addServlet(new ServletHolder(new LoginServlet(db, mixedMethods)), "/login/*");
        handler.addServlet(new ServletHolder(new LogOutServlet()), "/logout/*");
        handler.addServlet(new ServletHolder(new LikedProfilesServlet(db, marker, mixedMethods)), "/like/*");
        handler.addServlet(new ServletHolder(new MessagesServlet(db, marker, mixedMethods)), "/messages/*");
        handler.addServlet(new ServletHolder(new RegistrationServlet(db, mixedMethods)), "/register/*");
        handler.addServlet(new ServletHolder(new ReferenceServlet("CSS")), "/css/*");
        handler.addServlet(new ServletHolder(new MenuServlet(db,mixedMethods)),"/*");
        log.info("adding filters to servlets");
        handler.addFilter(CookieFilter.class, "/users/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/logout/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/like/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/login/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new ParamFilter("/login/*",mixedMethods)), "/login/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/register/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/messages/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new ParamFilter("/register/*",mixedMethods)), "/register/*", EnumSet.of(DispatcherType.REQUEST));
        log.info("Server is starting now");
        server.setHandler(handler);
        server.start();
        server.join();


    }


}
