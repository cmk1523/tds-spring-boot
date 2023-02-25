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
    protected final CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    SecurityConfiguration(final Environment environment,
                          final CustomAuthenticationManager customAuthenticationManager) {
        this.environment = environment;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
//                        "/*",
                        "/assets/**"
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

        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
////        String security = this.environment.getProperty("security.custom.system");
////
////        if (StringUtils.isNotEmpty(security) && security.equalsIgnoreCase("custom")) {
////            auth.authenticationProvider(this.customAuthenticationManager);
////        } else {
//            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//            UserDetails user = User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password(encoder.encode("password"))
//                    .roles("USER")
//                    .build();
//            return new InMemoryUserDetailsManager(user);
////        }
//    }

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


//    @Bean
//    public OpenAPI customOpenAPI() {
//        Contact contact = new Contact()
//            .name(this.environment.getProperty("swagger.contact.name"))
//            .url(this.environment.getProperty("swagger.contact.url"))
//            .email(this.environment.getProperty("swagger.contact.email"));
//
//        License license = new License()
//                .name(this.environment.getProperty("swagger.api.license"))
//                .url(this.environment.getProperty("swagger.api.license.url"));
//
//        return new OpenAPI()
//                .components(new Components())
//                .info(new Info()
//                        .title(this.environment.getProperty("swagger.title"))
//                        .description(this.environment.getProperty("swagger.description"))
//                        .version(this.environment.getProperty("application.version"))
//                        .termsOfService( this.environment.getProperty("swagger.tos.url"))
//                        .contact(contact)
//                        .license(license)
//                );
//    }


//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        String security = this.environment.getProperty("security.custom.system");
//
//        if (StringUtils.isNotEmpty(security) && security.equalsIgnoreCase("custom")) {
//            auth.authenticationProvider(this.customAuthenticationManager);
//        } else {
//            PasswordEncoder encoder =
//                    PasswordEncoderFactories.createDelegatingPasswordEncoder();
//            auth.inMemoryAuthentication()
//                    .withUser("user")
//                    .password(encoder.encode("password"))
//                    .roles("USER")
//                    .and()
//                    .withUser("admin").
//                    password(encoder.encode("admin"))
//                    .roles("USER", "API", "ADMIN");
//        }
//    }
}
