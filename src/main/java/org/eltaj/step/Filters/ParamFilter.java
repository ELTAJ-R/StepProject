package org.eltaj.step.Filters;

import lombok.extern.log4j.Log4j2;
import org.eltaj.step.DataBase.Methods;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
public class ParamFilter implements PostFilter {
    private String redirect;
    private Methods mixedMethods;

    public ParamFilter(String redirect, Methods mixedMethods) {
        this.redirect = redirect;
        this.mixedMethods = mixedMethods;
    }
    // no way to go to Login(POST)/Register(POST) servlet without filling all the fields of the form.
    @Override
    public void doPostFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        log.info("Checking whether all the necessary parameters have been passed");
        boolean mayContinue= mixedMethods.allIsFilled(req);
        if(mayContinue)chain.doFilter(req,resp);
        else resp.sendRedirect(redirect);
    }
    }

