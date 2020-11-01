package com.techdevsolutions.springBoot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = Logger.getLogger(ControllerInterceptor.class.getName());

    public String getRestURI(HttpServletRequest request) {
        return ControllerInterceptor.GetRestURI(request);
    }

    public static String GetRestURI(HttpServletRequest request) {
        return request.getMethod() + " - " + request.getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String user = "?";

        try {
            Authentication authentication = (Authentication) request.getUserPrincipal();
            user = ((UserDetails) authentication.getPrincipal()).getUsername();
        } catch (Exception e) {
            this.logger.severe(e.toString());
        }

        request.getSession().setAttribute("username", user);
        request.setAttribute("requestStartTime", new Date().getTime());
        this.logger.info(user + " - " + this.getRestURI(request) + "...");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long took = -1;
        String user = "?";

        try {
            long requestStartTime = (long) request.getAttribute("requestStartTime");
            took = new Date().getTime() - requestStartTime;
        } catch (Exception e) {
            this.logger.severe(e.toString());
        }

        try {
            Authentication authentication = (Authentication) request.getUserPrincipal();
            user = ((UserDetails) authentication.getPrincipal()).getUsername();
        } catch (Exception e) {
            this.logger.severe(e.toString());
        }

        this.logger.info(user + " - " + this.getRestURI(request) + "... [DONE] - took " + took + "ms");
    }
}
