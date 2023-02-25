package com.techdevsolutions.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
