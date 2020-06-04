package org.eltaj.step.Filters;

import org.eltaj.step.DataBase.Methods;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParamLoginFilter implements PostFilter {
    private final Methods mixedMethods = new Methods();

    // no way to go to Login(POST) servlet without filling all the fields of the form.
    @Override
    public void doPostFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
       boolean mayContinue= mixedMethods.allIsFilled(req);
       if(mayContinue)chain.doFilter(req,resp);
       else resp.sendRedirect("/login/*");
    }
}