package com.techdevsolutions.springBoot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class ControllerInterceptor implements HandlerInterceptor {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        try {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            user = principal.getAttribute("name");
        } catch (Exception ignored) {}

        request.setAttribute("requestStartTime", new Date().getTime());
        LOG.info(request.getMethod() + " " + request.getRequestURI() + " " + user);
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
