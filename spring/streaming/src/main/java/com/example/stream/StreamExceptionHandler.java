/*
package com.example.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

*/
/**
 * Created by tl on 5/26/17.
 * note
 * - global exception handler
 *//*

@RestControllerAdvice
public class StreamExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(StreamExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Object handleStreamException(HttpServletRequest request, Exception ex, WebRequest req){
        log.info(request.getMethod() + request.getContextPath() + request.getPathTranslated() + request.getRequestURI() + request.getQueryString());
        log.info(req.toString());
        return null;
    }

}
*/
