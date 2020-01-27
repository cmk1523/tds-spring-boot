package com.techdevsolutions.springBoot.security;

import com.techdevsolutions.springBoot.security.authorities.AdminAuthority;
import com.techdevsolutions.springBoot.security.authorities.ApiAuthority;
import com.techdevsolutions.springBoot.security.authorities.UserAuthority;
import com.techdevsolutions.springBoot.security.principals.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationManager implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Environment environment;
    private List<CustomUserDetails> users = new ArrayList<>();

    @Autowired
    public CustomAuthenticationManager(Environment environment) {
        this.environment = environment;

        String userPassword = this.environment.getProperty("security.custom.user.password");
        CustomUserDetails user = new CustomUserDetails();
        user.setUsername("user");
        user.setPassword(StringUtils.isNotEmpty(userPassword) ? userPassword : "password");
        user.setAuthorities(new ArrayList<>(Arrays.asList(new UserAuthority())));
        this.users.add(user);

        String adminPassword = this.environment.getProperty("security.custom.admin.password");
        CustomUserDetails admin = new CustomUserDetails();
        admin.setUsername("admin");
        admin.setPassword(StringUtils.isNotEmpty(adminPassword) ? adminPassword : "password");
        admin.setAuthorities(new ArrayList<>(Arrays.asList(new UserAuthority(), new ApiAuthority(), new AdminAuthority())));
        this.users.add(admin);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Optional<CustomUserDetails> found = this.users.stream()
                    .filter((i)-> username.equalsIgnoreCase(i.getUsername()) && password.equals(i.getPassword()))
                    .findFirst();

            if (found.isPresent()) {
                this.logger.info("Authenticating: " + username + "... [SUCCESS]");
                CustomUserDetails user = found.get();
                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
            } else {
                this.logger.warn("Authenticating: " + username + "... [FAILED]");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Authentication exception for: " + username + ". Exception: " + e.toString());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
