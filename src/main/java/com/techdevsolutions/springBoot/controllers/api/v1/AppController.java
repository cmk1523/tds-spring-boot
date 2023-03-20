package com.techdevsolutions.springBoot.controllers.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techdevsolutions.springBoot.beans.Response;
import com.techdevsolutions.springBoot.controllers.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/app")
@Tag(name = "app-controller", description = "Application information")
public class AppController extends BaseController {
    private final Environment environment;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
        map.put("apiLicenseUrl", environment.getProperty("swagger.api.license.url"));

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("anonymousUser")) {
                map.put("user", null);
            } else {

                Map<String, Object> principalMap = (Map<String, Object>) this.objectMapper.convertValue(authentication.getPrincipal(), Map.class);

                Map<String, Object> attributes = principalMap.get("attributes") != null
                        ? (Map<String, Object>) principalMap.get("attributes") // github & google
                        : principalMap; // local

                Map<String, Object> returnMap = new HashMap<>();
                returnMap.put("id", attributes.get("id") != null
                        ? attributes.get("id") // github and local
                        : attributes.get("sub")); // google
                returnMap.put("login", attributes.get("login") != null
                        ? attributes.get("login") // github
                        : attributes.get("email") != null
                        ? attributes.get("email") // google
                        : attributes.get("username")); // local
                returnMap.put("name", attributes.get("name") != null
                        ? attributes.get("name") // github & google
                        : attributes.get("username")); // local
                returnMap.put("avatar", attributes.get("avatar_url") != null
                        ? attributes.get("avatar_url") // github
                        : attributes.get("picture")); // google
                returnMap.put("authorities", attributes.get("authorities"));

                map.put("user", returnMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(null));
        }

        return new Response(map, this.getTimeTook(request));
    }
}
