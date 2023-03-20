package com.techdevsolutions.springBoot;

import com.techdevsolutions.springBoot.security.CustomAuthenticationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final Environment environment;

    @Autowired
    SecurityConfiguration(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .authorizeRequests()
                    .antMatchers(
                        "/login",
                        "/assets/**",
                        "/api/v1/app"
                    )
                    .permitAll()
                    .antMatchers("/api/v1/**")
                    .   hasRole("API")
                    .anyRequest()
                    .authenticated()
            .and()
                .httpBasic()
            .and()
                .formLogin()
                .loginPage("/index")
                .permitAll()
            .and()
                .oauth2Login()
                .loginPage("/index")
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();

        return http.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(environment.getProperty("application.title"),
                environment.getProperty("swagger.description"),
                environment.getProperty("application.version"),
                environment.getProperty("swagger.tos.url"),
                new Contact(
                        environment.getProperty("swagger.contact.name"),
                        environment.getProperty("swagger.contact.url"),
                        environment.getProperty("swagger.contact.url")
                ),
                environment.getProperty("swagger.api.license"),
                environment.getProperty("swagger.api.license.url"),
                Collections.emptyList());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.techdevsolutions"))
                .paths(PathSelectors.any())
                .build();
    }

}
