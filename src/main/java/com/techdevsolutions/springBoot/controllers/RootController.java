package com.techdevsolutions.springBoot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
public class RootController {
    @RequestMapping(method = RequestMethod.GET)
    public String swaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}
