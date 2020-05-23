import org.eltaj.step.DataBase.Updater.SQLUpdaterApp;
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
        HerokuEnv env=new HerokuEnv();
        SQLUpdaterApp db = new SQLUpdaterApp();
        db.autoUpdate();
        Server server = new Server(env.port());
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new UsersServlet()), "/users/*");
        handler.addServlet(new ServletHolder(new LoginServlet()), "/login/*");
        handler.addServlet(new ServletHolder(new LogOutServlet()), "/logout/*");
        handler.addServlet(new ServletHolder(new LikedProfilesServlet()), "/like/*");
        handler.addServlet(new ServletHolder(new MessagesServlet()), "/messages/*");
        handler.addServlet(RegistrationServlet.class, "/register/*");
        handler.addServlet(CookiesGet.class, "/cookies/*");
        handler.addServlet(new ServletHolder(new ReferenceServlet("CSS")), "/css/*");

        handler.addFilter(CookieFilter.class, "/users/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/logout/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/like/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/login/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(LoginFilter.class, "/register/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(CookieFilter.class, "/messages/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(handler);
        server.start();
        server.join();


    }


}
