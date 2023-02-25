package com.techdevsolutions.springBoot.controllers;

import com.techdevsolutions.springBoot.beans.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleException(final Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(ex.getMessage()));
//        return "error";
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Response> handleException(final RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(ex.getMessage()));
//        return "error";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Response> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(ex.getMessage()));
//        return "400";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response> handleAccessDeniedException(final AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(ex.getMessage()));
//        return "403";
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Response> handleNoSuchElementException(final NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(ex.getMessage()));
//        return "404";
    }
}
