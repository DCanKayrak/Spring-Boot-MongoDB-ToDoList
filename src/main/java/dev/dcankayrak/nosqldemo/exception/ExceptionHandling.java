package dev.dcankayrak.nosqldemo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(UserException.class)
    public String handleUserNotFound(UserException ex){
        return ex.getMessage();
    }
}
