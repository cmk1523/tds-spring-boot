package com.techdevsolutions.springBoot;

import com.techdevsolutions.springBoot.controllers.ControllerInterceptor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
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
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
            .name(this.environment.getProperty("swagger.contact.name"))
            .url(this.environment.getProperty("swagger.contact.url"))
            .email(this.environment.getProperty("swagger.contact.email"));

        License license = new License()
                .name(this.environment.getProperty("swagger.api.license"))
                .url(this.environment.getProperty("swagger.api.license.url"));

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(this.environment.getProperty("swagger.title"))
                        .description(this.environment.getProperty("swagger.description"))
                        .version(this.environment.getProperty("application.version"))
                        .termsOfService( this.environment.getProperty("swagger.tos.url"))
                        .contact(contact)
                        .license(license)
                );
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.techdevsolutions.springBoot.controllers"))
//                .paths(PathSelectors.ant("/api/v1/**"))
//                .build()
//                .pathMapping("/")
//                .apiInfo(apiInfo());
//
//    }

//    private ApiInfo apiInfo() {
//        Contact contact = new Contact(this.environment.getProperty("swagger.contact.name"),
//                this.environment.getProperty("swagger.contact.url"),
//                this.environment.getProperty("swagger.contact.email"));
//
//        return new ApiInfo(
//                this.environment.getProperty("swagger.title"),
//                this.environment.getProperty("swagger.description"),
//                this.environment.getProperty("application.version"),
//                this.environment.getProperty("swagger.tos.url"),
//                contact,
//                this.environment.getProperty("swagger.api.license"),
//                this.environment.getProperty("swagger.api.license.url"),
//                Collections.emptyList());
//    }
}