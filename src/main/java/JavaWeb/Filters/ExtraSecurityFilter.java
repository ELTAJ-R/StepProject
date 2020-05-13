package JavaWeb.Filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExtraSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain Chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getMethod().equals("POST")) {
            try {
                Cookie[] cookies = req.getCookies();
                if (cookies.length == 0) resp.sendRedirect("/login/*");
                else Chain.doFilter(req, resp);
            } catch (NullPointerException e) {
                resp.sendRedirect("/login/*");
            }
        }


    }


    @Override
    public void destroy() {

    }
}
