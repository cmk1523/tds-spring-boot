package com.techdevsolutions.springBoot.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class ApiAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_API";
    }
}
