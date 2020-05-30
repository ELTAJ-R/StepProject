import org.eltaj.step.DataBase.FreeMarker;
import org.eltaj.step.DataBase.Methods;
import org.eltaj.step.DataBase.SQL;
import org.eltaj.step.DataBase.Updater.InitializeSQL;
import org.eltaj.step.DataBase.HerokuEnv;
import org.eltaj.step.Filters.CookieFilter;
import org.eltaj.step.Filters.LoginFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eltaj.step.JavaWeb.*;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class WebServerApp {
    public static void main(String[] args) throws Exception {

        // initializing methods and classes for all servlets
        HerokuEnv env=new HerokuEnv();
        InitializeSQL updater = new InitializeSQL();
        Methods mixedMethods = new Methods();
        SQL db=new SQL(mixedMethods);
        FreeMarker marker=new FreeMarker(db.htmlLocation);

        // updates or creates database prior to starting server
        updater.autoUpdate();


        // Configuring server and mapping to URL
        Server server = new Server(env.port());
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new UsersServlet(db,marker, mixedMethods)), "/users/*");
        handler.addServlet(new ServletHolder(new LoginServlet(db,marker, mixedMethods)), "/login/*");
        handler.addServlet(new ServletHolder(new LoginServlet(db,marker, mixedMethods)), "/");
        handler.addServlet(new ServletHolder(new LogOutServlet()), "/logout/*");
        handler.addServlet(new ServletHolder(new LikedProfilesServlet(db,marker, mixedMethods)), "/like/*");
        handler.addServlet(new ServletHolder(new MessagesServlet(db,marker, mixedMethods)), "/messages/*");
        handler.addServlet(new ServletHolder(new RegistrationServlet(db,marker, mixedMethods)), "/register/*");
        handler.addServlet(new ServletHolder(new ReferenceServlet("CSS")), "/css/*");

        handler.addFilter(CookieFilter.class, "/users/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/logout/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/like/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/login/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/register/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/messages/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(handler);
        server.start();
        server.join();


    }


}
