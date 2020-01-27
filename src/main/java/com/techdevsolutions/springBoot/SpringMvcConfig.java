package com.techdevsolutions.springBoot;

import com.techdevsolutions.springBoot.controllers.ControllerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringMvcConfig implements WebMvcConfigurer {
    private Environment environment;

    @Autowired
    public SpringMvcConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Need thymeleaf for this to work
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

//    @Bean
//    ControllerInterceptor controllerInterceptor() {
//        return new ControllerInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor())
                .addPathPatterns("/api/**");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.techdevsolutions.springBoot.controllers"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(this.environment.getProperty("swagger.contact.name"),
                this.environment.getProperty("swagger.contact.url"),
                this.environment.getProperty("swagger.contact.email"));

        return new ApiInfo(
                this.environment.getProperty("swagger.title"),
                this.environment.getProperty("swagger.description"),
                this.environment.getProperty("application.version"),
                this.environment.getProperty("swagger.tos.url"),
                contact,
                this.environment.getProperty("swagger.api.license"),
                this.environment.getProperty("swagger.api.license.url"),
                Collections.emptyList());
    }
}