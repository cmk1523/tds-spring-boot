package com.techdevsolutions.springBoot;

import com.techdevsolutions.springBoot.controllers.ControllerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    private final Environment environment;

    @Autowired
    public SpringMvcConfig(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        // Need thymeleaf for this to work
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor())
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
