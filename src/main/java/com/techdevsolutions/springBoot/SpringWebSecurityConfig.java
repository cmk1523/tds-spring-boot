package com.techdevsolutions.springBoot;

import com.techdevsolutions.springBoot.security.CustomAuthenticationManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = Logger.getLogger(SpringWebSecurityConfig.class.getName());
    private Environment environment;
    protected CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    SpringWebSecurityConfig(Environment environment, CustomAuthenticationManager customAuthenticationManager) {
        this.environment = environment;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers(
//                        "/*",
//                        "/assets/**"
                )
                .permitAll()
                .antMatchers("/api/v1/app/**")
                .hasRole("USER")
                .antMatchers("/api/v1/**")
                .hasRole("API")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        String security = this.environment.getProperty("security.custom.system");

        if (StringUtils.isNotEmpty(security) && security.equalsIgnoreCase("custom")) {
            auth.authenticationProvider(this.customAuthenticationManager);
        } else {
            PasswordEncoder encoder =
                    PasswordEncoderFactories.createDelegatingPasswordEncoder();
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(encoder.encode("password"))
                    .roles("USER")
                    .and()
                    .withUser("admin").
                    password(encoder.encode("admin"))
                    .roles("USER", "API", "ADMIN");
        }
    }
}