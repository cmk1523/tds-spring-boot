package com.techdevsolutions.springBoot.controllers.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.springBoot.beans.Response;
import com.techdevsolutions.springBoot.controllers.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app")
@Tag(name = "app-controller", description = "Application information")
public class AppController extends BaseController {
    private final Environment environment;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public AppController(final Environment environment) {
        this.environment = environment;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object search(final HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", environment.getProperty("application.title"));
        map.put("name", environment.getProperty("application.name"));
        map.put("organization", environment.getProperty("application.organization"));
        map.put("version", environment.getProperty("application.version"));
        map.put("buildNumber", environment.getProperty("application.buildNumber"));
        map.put("buildDateTime", environment.getProperty("application.buildDateTime"));
        map.put("environment", environment.getProperty("spring.profiles.active"));

        map.put("description", environment.getProperty("swagger.description"));
        map.put("termsOfService", environment.getProperty("swagger.tos.url"));
        map.put("contactName", environment.getProperty("swagger.contact.name"));
        map.put("contactUrl", environment.getProperty("swagger.contact.url"));
        map.put("contactEmail", environment.getProperty("swagger.contact.email"));
        map.put("apiLicense", environment.getProperty("swagger.api.license"));
        map.put("apiLicenseUrl", environment.getProperty("swagger.api.license.url"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Map<String, Object> principalMap = (Map<String, Object>) this.objectMapper.convertValue(principal, Map.class);
        principalMap.remove("password");
        map.put("user", principalMap);
        return new Response(map, this.getTimeTook(request));
    }

}
