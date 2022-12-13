package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

@ControllerAdvice
public class ParseExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler({ParseException.class, JsonProcessingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String parseExceptionHandler(ParseException ex) {
        return ex.getMessage();
    }
}