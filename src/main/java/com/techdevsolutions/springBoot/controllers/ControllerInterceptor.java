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
        return request.getMethod() + " " + request.getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.logger.info(this.getRestURI(request));
        Authentication authentication = (Authentication) request.getUserPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.getSession().setAttribute("username", userDetails.getUsername());
        request.setAttribute("requestStartTime", new Date().getTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}