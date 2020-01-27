package com.techdevsolutions.springBoot.controllers;

import com.techdevsolutions.springBoot.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/app")
public class AppController extends BaseController {
    private Environment environment;

    @Autowired
    public AppController(Environment environment) {
        this.environment = environment;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", environment.getProperty("application.name"));
            map.put("title", environment.getProperty("application.title"));
            map.put("version", environment.getProperty("application.version"));
            map.put("buildNumber", environment.getProperty("application.buildNumber"));
            map.put("buildDateTime", environment.getProperty("application.buildDateTime"));

            Authentication authentication = (Authentication) request.getUserPrincipal();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> userObject = new HashMap<>();
            userObject.put("username", userDetails.getUsername());
            userObject.put("authorities", userDetails.getAuthorities());

            map.put("user", userObject);
            return new Response(map, this.getTimeTook(request));
        } catch (Exception e) {
            e.printStackTrace();
            return this.generateErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

}