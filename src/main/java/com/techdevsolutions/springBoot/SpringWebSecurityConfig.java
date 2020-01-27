package com.techdevsolutions.springBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

//    protected CustomAuthenticationManager customAuthenticationManager;
//
//    @Autowired
//    SpringWebSecurityConfig(CustomAuthenticationManager customAuthenticationManager) {
//        this.customAuthenticationManager = customAuthenticationManager;
//    }

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
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
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

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(this.customAuthenticationManager);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}