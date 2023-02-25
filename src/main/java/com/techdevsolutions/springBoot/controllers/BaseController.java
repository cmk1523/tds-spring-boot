package com.techdevsolutions.springBoot.controllers;

import com.techdevsolutions.springBoot.beans.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class BaseController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    public Long getTimeTook(HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute("requestStartTime");
        return new Date().getTime() - startTime;
    }
}
