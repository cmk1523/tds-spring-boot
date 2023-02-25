package com.techdevsolutions.springBoot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class ControllerInterceptor implements HandlerInterceptor {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public String getRestURI(HttpServletRequest request) {
        return ControllerInterceptor.GetRestURI(request);
    }

    public static String GetRestURI(HttpServletRequest request) {
        return request.getMethod() + " " + request.getRequestURI();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setAttribute("requestStartTime", new Date().getTime());
        LOG.info(this.getRestURI(request) + " " + user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        long took = -1;
//        String user = "?";
//
//        try {
//            long requestStartTime = (long) request.getAttribute("requestStartTime");
//            took = new Date().getTime() - requestStartTime;
//        } catch (Exception e) {
//            LOG.error(e.toString());
//        }
//
//        try {
//            Authentication authentication = (Authentication) request.getUserPrincipal();
//            user = ((UserDetails) authentication.getPrincipal()).getUsername();
//        } catch (Exception e) {
//            LOG.error(e.toString());
//        }
//
//        LOG.info(this.getRestURI(request) + " " + user);
    }
}
